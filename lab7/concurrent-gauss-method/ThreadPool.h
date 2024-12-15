#pragma once

#include <condition_variable>
#include <functional>
#include <iostream>
#include <mutex>
#include <queue>
#include <thread>

class ThreadPool {
    public:
        explicit ThreadPool(size_t numberOfThreads);
        ~ThreadPool();

        void submit(std::function<void()> task);
        void wait();

private:
    std::vector<std::thread> threads_;
    std::queue<std::function<void()> > tasks_;

    std::mutex queue_mutex_;
    std::condition_variable cv_;
    std::condition_variable cv_wait_;

    bool stop_ = false;
    int active_tasks_ = 0;
};
