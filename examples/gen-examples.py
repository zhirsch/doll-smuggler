#!/usr/bin/python -tt

# Converts examples from https://people.sc.fsu.edu/~jburkardt/datasets/knapsack_01/knapsack_01.html
# into the format expected by this solver.
#
# NOTE: the p08 example from that site takes too long for this solver to solve.

import sys

def capacity_path(x):
    return '%sc.txt' % x
def weights_path(x):
    return '%sw.txt' % x
def values_path(x):
    return '%sp.txt' % x
def solution_path(x):
    return '%ss.txt' % x

def main(args):
    max_weight = int(open(capacity_path(args[0])).read())
    weights = [int(x) for x in open(weights_path(args[0]))]
    values = [int(x) for x in open(values_path(args[0]))]
    solution = [bool(int(x)) for x in open(solution_path(args[0]))]

    with open('input.txt', 'w') as f:
        f.write('max weight: %d\n' % max_weight)
        f.write('\n')
        f.write('available dolls:\n')
        f.write('\n')
        f.write('name    weight value\n')
        for i in range(len(weights)):
            name = 'name_%d' % i
            f.write('%-8s %4d %5d\n' % (name, weights[i], values[i]))

    with open('output.txt', 'w') as f:
        f.write('packed dolls:\n')
        f.write('\n')
        f.write('name    weight value\n')
        for i in reversed(range(len(weights))):
            if solution[i]:
                name = 'name_%d' % i
                f.write('%-8s %4d %5d\n' % (name, weights[i], values[i]))


if __name__ == '__main__':
    sys.exit(main(sys.argv[1:]))
