# Kamil Matejuk
import math


def pascalRow(above, n):
    margin = 4
    if len(above) == 0:
        for i in range(1, margin * n - 2):
            print(" ", end="")
        print(1)
        pascalRow([1], n)
    elif len(above) <= n:
        new = [1]
        for i in range(len(above) - 1):
            new.append(above[i] + above[i + 1])
        new.append(1)
        word = ""
        for j in new:
            word += (str(j) + "   ")
        spaces = margin * n - math.floor(len(word) / 2)
        for i in range(1, spaces):
            print(" ", end="")
        print(word)
        pascalRow(new, n)


def pascal_triangle(n):
    pascalRow([], n)


if __name__ == "__main__":
    pascal_triangle(17)
