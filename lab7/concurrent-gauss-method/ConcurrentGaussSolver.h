#pragma once

#include <iostream>
#include <string>
#include <vector>

class ConcurrentGaussSolver {
public:
    explicit ConcurrentGaussSolver(const std::string &filename);

    void printM() const;
    void solve();
    void backwardSubstitution();

private:
    int size_;
    std::vector<std::vector<double>> M_;
    std::vector<double> m_;
    std::vector<std::vector<double>> n_;

    void A(int k, int i);
    void B(int k, int j, int i);
    void C(int k, int j, int i);

    void scheduleA(int i);
    void scheduleB(int i);
    void scheduleC(int i);
};

