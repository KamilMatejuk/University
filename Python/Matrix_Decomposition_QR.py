import os
import sys
import math
import numpy as np


def qr(A):
    """ Decompose matrix A into QR using Householder transformations

    Args:
        A (np.array): starting matrix A

    Returns:
        Q (np.array): result matrix Q from decomposition A = QR
        R (np.array): result matrix R from decomposition A = QR
    """
    # dimentions of matrix
    m, n = A.shape
    # matrix with ones on the diagonal and zeros elsewhere
    Q = np.diag([1.0] * m)
    # number of iterations
    rng = n - 1 if n == m else n
    for i in range(rng):
        # matrix with ones on the diagonal and zeros elsewhere
        H = np.diag([1.0] * m)
        # convert part of the diagonal matrix using Householder tranformation,
        # into i-th Householder matrix
        H[i:, i:] = householder_transformation(A[i:, i])
        # calculate Q using recursively Q * Hi
        Q = np.dot(Q, H)
        # calculate R using recursively Hi * A
        A = np.dot(H, A)
    # clip metrices into required size
    return Q[:, :n], np.triu(A[:n])


def householder_transformation(a):
    """ Generate a Householder matrix

    Args:
        a (np.array): vector from matrix A

    Returns:
        np.array: Householder matrix
    """
    # euclidean norm of the vector
    norm = math.sqrt(sum(ai**2 for ai in a))
    # copy sign of the first element of vector
    norm = norm if a[0] >= 0 else -norm
    # calculate vector v
    v = a / (a[0] + norm)
    v[0] = 1
    # create identity matrix (1 on the diagonal, elsewhere 0)
    H = np.diag([1.0] * a.shape[0])
    # dot product of vector v with self
    dp = np.dot(v, v)
    # outer product of vector v with transposed self
    op = np.dot(v[:, None], v[None, :])
    # substract from identity matrix
    H -= (2 / dp) * op
    return H


def show(A, Q, R):
    """ display results

    Args:
        A (np.array): starting matrix A
        Q (np.array): result matrix Q from decomposition A = QR
        R (np.array): result matrix R from decomposition A = QR
    """
    compare = '--compare' in sys.argv
    q, r = np.linalg.qr(A)
    with np.printoptions(
        precision=6,  # precision of each value in matrix
        suppress=True  # dont use scientific notation for small values
    ):
        print('Staring matrix A:\n')
        print(A)
        print()
        print("matrix Q from QR decomposition:\n")
        print(Q)
        if compare:
            print("\nmatrix Q from np.linalg.qr:\n")
            print(q)
        print()
        print("matrix R from QR decomposition:\n")
        print(R)
        if compare:
            print("\nmatrix R from np.linalg.qr:\n")
            print(r)


def getMatrix(args):
    """ check what type of matrix to read, and return this matrix

    Args:
        args (list): arguments from commandline

    Returns:
        np.array: matrix A
    """
    # generate random matrix
    if ('--random') in args:
        i = args.index('--random')
        if len(args) > i + 2:
            if args[i + 1].isdigit() and args[i + 2].isdigit():
                m = int(args[i + 1])
                n = int(args[i + 2])
                if m >= n:
                    return np.random.rand(m, n)
        return None
    # read matrix from file
    if ('--file') in args:
        i = args.index('--file')
        if len(args) > i + 1:
            if os.path.isfile(args[i + 1]):
                with open(args[i + 1], 'r') as f:
                    A = [[float(i) for i in line.split(' ')]
                         for line in f.readlines()]
                    return np.array(A)
        return None
    # read matrix from standard input
    m = input("Input matrix size (bigger): ")
    n = input("Input matrix size (smaller): ")
    if m.isdigit() and n.isdigit():
        m = int(m)
        n = int(n)
        if m >= n:
            A = [[0.0 for _ in range(n)] for _ in range(m)]
            for row in range(m):
                line = input("Input row of matrix (values divided by space): ")
                line = [float(i) for i in line.split(' ')[:n]]
                for index, value in enumerate(line):
                    A[row][index] = value
            return np.array(A)
    return None


###
# main run of the program
###
if __name__ == '__main__':
    # get matrix
    A = getMatrix(sys.argv)
    if A is None:
        print('Incorrect values')
        exit(0)
    # decompose A = QR
    Q, R = qr(A)
    show(A, Q, R)


###
# how to run the program:
#
# python3 main.py                          <- read starting matrix from standard input (commandline)
# python3 main.py --random m n             <- generate random matrix of size (m, n), where m >= n
# python3 main.py --file filename          <- read matrix from file (values divided by spaces)
#
# additional flag '--compare', to show comparison of results with the ones
# calculated by np.linalg.qr()
# e.g.
# python3 main.py --compare
# python3 main.py --random m n --compare
# python3 main.py --file filename --compare
###
