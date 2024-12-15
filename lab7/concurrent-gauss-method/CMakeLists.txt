cmake_minimum_required(VERSION 3.30)
project(concurrent_gauss_method)

set(CMAKE_CXX_STANDARD 23)

add_executable(concurrent_gauss_method main.cpp
        ConcurrentGaussSolver.cpp
        ConcurrentGaussSolver.h
        ThreadPool.cpp
        ThreadPool.h
)
