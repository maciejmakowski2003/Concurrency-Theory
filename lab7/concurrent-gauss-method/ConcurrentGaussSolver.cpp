#include "ConcurrentGaussSolver.h"

#include <stdexcept>
#include <fstream>
#include <thread>
#include <future>

ConcurrentGaussSolver::ConcurrentGaussSolver(const std::string &filename) {
    std::ifstream file(filename);
    if (!file.is_open()) {
        throw std::runtime_error("Could not open file " + filename);
    }

    int size;
    file >> size;

    if (size < 1) {
        throw std::runtime_error("Matrix size could not be equal to 0 " + filename);
    }

    size_ = size;

    M_ = std::vector(size_, std::vector(size_ + 1, 0.0));

    for (int i = 0; i < size_; ++i) {
        for (int j = 0; j < size_; ++j) {
            if (!(file >> M_[i][j])) {
                throw std::runtime_error("Error reading matrix data");
            }
        }
    }

    for (int i = 0; i < size_; i++) {
        if (!(file >> M_[i][size_])) {
            throw std::runtime_error("Error reading matrix data");
        }
    }

    file.close();

    m_ = std::vector(size_, 0.0);
    n_ = std::vector(size_, std::vector(size_ + 1, 0.0));

    threadPool_ = std::make_unique<ThreadPool>(std::thread::hardware_concurrency());
}

void ConcurrentGaussSolver::printM() const {
    std::cout << std::endl;
    for (int i = 0; i < size_; i++) {
        for (int j = 0; j < size_; j++) {
            const auto value = M_[i][j] > 1e-6 ? M_[i][j] : 0.0;
            std::cout << value  << " ";
        }
        std::cout << std::endl;
    }

    for (int i = 0; i < size_; i++) {
        std::cout << M_[i][size_] << " ";
    }
}

void ConcurrentGaussSolver::solve() {
    for (int i = 0; i < size_; ++i) {
        scheduleA(i);
        scheduleB(i);
        scheduleC(i);
    }
}

void ConcurrentGaussSolver::backwardSubstitution() {
    M_[size_ - 1][size_] /= M_[size_ - 1][size_ - 1];
    M_[size_ - 1][size_ - 1] = 1.0;

    for (int i = size_ - 2; i >= 0; --i) {
        for (int j = i + 1; j < size_; ++j) {
            M_[i][size_] -= M_[i][j] * M_[j][size_];
            M_[i][j] = 0.0;
        }

        M_[i][size_] /= M_[i][i];
        M_[i][i] = 1.0;
    }
}

void ConcurrentGaussSolver::A(const int k, const int i) {
    m_[k] = M_[k][i] / M_[i][i];
}

void ConcurrentGaussSolver::B(const int k, const int j, const int i) {
    n_[k][j] = M_[i][j] * m_[k];
}

void ConcurrentGaussSolver::C(const int k, const int j, const int i) {
    M_[k][j] = M_[k][j] - n_[k][j];
}

void ConcurrentGaussSolver::scheduleA(const int i) {
    for (int k = i + 1; k < size_; ++k) {
        threadPool_->submit([this, k, i]() { A(k, i); });
    }

    threadPool_->wait();
}

void ConcurrentGaussSolver::scheduleB(const int i) {
    for (int k = i + 1; k < size_; ++k) {
        for (int j = i; j < size_ + 1; ++j) {
            threadPool_->submit([this, k, j, i]() { B(k, j, i); });
        }
    }

    threadPool_->wait();
}

void ConcurrentGaussSolver::scheduleC(const int i) {
    for (int k = i + 1; k < size_; ++k) {
        for (int j = i; j < size_ + 1; ++j) {
            threadPool_->submit([this, k, j, i]() { C(k, j, i); });
        }
    }

    threadPool_->wait();
}

