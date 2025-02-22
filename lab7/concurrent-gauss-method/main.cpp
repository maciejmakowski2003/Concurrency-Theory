#include <string>

#include "ConcurrentGaussSolver.h"

int main() {
    const std::string filename = "../input2.txt";
    const auto solver = new ConcurrentGaussSolver(filename);

    solver->solve();
    solver->backwardSubstitution();
    solver->saveToFile("../solution.txt");

    return 0;
}
