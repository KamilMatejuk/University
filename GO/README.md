## Contents
* `Concurrency` [Graph_Concurrency](#graph-concurrency)

## Topics
### Graph_Concurrency
Create a directional, cyclic graph with some additional edges (shortcuts for bigger cycle). Send an array of packets through this graph, from `0` to `n-1` vertex. Each packet is an integer. Packet can travel to the next vertex, if it is not occupied. Each of vertices, as well as sender and reciever, are different threads, and use Go channels to communicate. 
