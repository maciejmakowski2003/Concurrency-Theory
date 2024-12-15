#include "ThreadPool.h"

ThreadPool::ThreadPool(const size_t numberOfThreads) {
    for (size_t i = 0; i < numberOfThreads; ++i) {
        threads_.emplace_back([this] {
            while (true) {
                std::function<void()> task;
                {
                    std::unique_lock lock(queue_mutex_);
                    cv_.wait(lock, [this] { return !tasks_.empty() || stop_;});

                    if (stop_ && tasks_.empty()) {
                        return;
                    }

                    task = move(tasks_.front());
                    tasks_.pop();
                    ++active_tasks_;
                }

                task();
                {
                    std::unique_lock lock(queue_mutex_);
                    --active_tasks_;
                    if (tasks_.empty() && active_tasks_ == 0) {
                        cv_wait_.notify_all();
                    }
                }
            }
        });
    }
}

ThreadPool::~ThreadPool()
{
    {
        std::unique_lock lock(queue_mutex_);
        stop_ = true;
    }

    cv_.notify_all();

    for (auto& thread : threads_) {
        thread.join();
    }
}

void ThreadPool::submit(std::function<void()> task) {
    {
        std::unique_lock lock(queue_mutex_);
        tasks_.emplace(move(task));
    }
    cv_.notify_one();
}

void ThreadPool::wait() {
    std::unique_lock lock(queue_mutex_);
    cv_wait_.wait(lock, [this] { return tasks_.empty() && active_tasks_ == 0; });
}

