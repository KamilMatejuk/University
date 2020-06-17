# Kamil Matejuk
import numpy as np


np.random.seed(17)


def sigmoid(x):
    return 1.0 / (1 + np.exp(-x))


def sigmoid_derivative(x):
    return x * (1.0 - x)


def relu(x):
    return np.maximum(0, x)


def relu_derivative(x):
    # https://jamesmccaffrey.wordpress.com/2017/06/23/two-ways-to-deal-with-the-derivative-of-the-relu-function/
    # przybliżenie funkcji Relu można wziąć jako:
    # relu ~ ln(1 + e^x)
    # wtedy pochodna jest funkcją sigmoid
    # return sigmoid(x)
    return sigmoid(x)


class NeuralNetwork(object):
    def __init__(self, x, y, functions):
        self.input = x
        self.weights1 = np.random.rand(4, x.shape[1])
        self.weights2 = np.random.rand(1, 4)
        self.y = y
        self.output = np.zeros(y.shape)
        self.eta = 0.03
        assert len(functions) == 4
        self.function1 = functions[0]
        self.derivative1 = functions[1]
        self.function2 = functions[2]
        self.derivative2 = functions[3]

    def feedforward(self):
        self.layer1 = self.function1(np.dot(self.input, self.weights1.T))
        self.output = self.function2(np.dot(self.layer1, self.weights2.T))

    def backprop(self):
        delta2 = (self.y - self.output) * self.derivative2(self.output)
        d_weights2 = self.eta * np.dot(delta2.T, self.layer1)

        delta1 = self.derivative1(self.layer1) * np.dot(delta2, self.weights2)
        d_weights1 = self.eta * np.dot(delta1.T, self.input)

        self.weights1 += d_weights1
        self.weights2 += d_weights2


def mean_square_error(output, values):
    diff = 0.0
    for i in range(len(output)):
        d = float(output[i][0]) - float(values[i][0])
        diff += d**2
    return diff


def testFunctions(X, Y):
    functions = [[sigmoid, sigmoid_derivative, sigmoid, sigmoid_derivative],
                 [relu, relu_derivative, relu, relu_derivative],
                 [sigmoid, sigmoid_derivative, relu, relu_derivative],
                 [relu, relu_derivative, sigmoid, sigmoid_derivative]]

    print('funkcja w ukrytej | funkcja w wyjściowej | mean squared error')
    for f in functions:
        network = NeuralNetwork(X, Y, f)
        # train
        for i in range(5000):
            network.feedforward()
            network.backprop()
        # show results
        print(f[0].__name__, end=''.join(' ' for i in range(
            len('funkcja w ukrytej | ') - len(f[0].__name__))))
        print(f[2].__name__, end=''.join(' ' for i in range(
            len('funkcja w wyjściowej | ') - len(f[2].__name__))))
        print(mean_square_error(network.output, Y))


if __name__ == '__main__':
    print('------------------------ XOR problem ------------------------')
    X = np.array([[0, 0, 1],
                  [0, 1, 1],
                  [1, 0, 1],
                  [1, 1, 1]])
    Y = np.array([[0], [1], [1], [0]])
    testFunctions(X, Y)
    print()

    print('------------------------ OR problem -------------------------')
    X = np.array([[0, 0, 1],
                  [0, 1, 1],
                  [1, 0, 1],
                  [1, 1, 1]])
    Y = np.array([[0], [1], [1], [1]])
    testFunctions(X, Y)
    print()

    print('------------------------ AND problem ------------------------')
    X = np.array([[0, 0, 1],
                  [0, 1, 1],
                  [1, 0, 1],
                  [1, 1, 1]])
    Y = np.array([[0], [0], [0], [1]])
    testFunctions(X, Y)

'''
W danych wejściowych ostatnia kolumna zawiera same jedynki,
ponieważ jest to tzw 'bias'. Pozwala to lepiej dopasować
funkcję aktywacji.
'''
