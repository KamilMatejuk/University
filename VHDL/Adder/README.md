# Adder
Adder takes 3 bits in, two bits to add (`i1`, `i2`) and carry (`ci`), and outputs one bit with result (`s`) and new carry (`co`).

Test bench for adder checks all 8 options for sending one bit each:
| i1 | i2 | ci | s | co |
|:--:|:--:|:--:|:-:|:--:|
|0|0|0|0|0|
|0|0|1|1|0|
|0|1|0|1|0|
|0|1|1|0|1|
|1|0|0|1|0|
|1|0|1|0|1|
|1|1|0|0|1|
|1|1|1|1|1|
