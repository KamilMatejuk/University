# XAND
This is a simple XAND gate, each time evaluates 2 bits in (`a`, `b`), and outputs one bit with result (`x`).

The difference is that it works on vectors of bits, not just one bit, so the input can be long binary value. The length is specified when creating component, using `generic` keyword.

Test bench checks 4 differnet versions of 8-bit vectors:
| a | b | x |
|:-:|:-:|:-:|
|00000000|00000000|11111111|
|11111111|11111111|11111111|
|01010101|01010101|11111111|
|01010101|10101010|00000000|
|11010111|00100010|00001010|
