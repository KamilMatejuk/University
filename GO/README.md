## Contents
* `Concurrency` [Packet_Graph](#packet-graph)
* `Concurrency` [Packet_Graph_With_Lifetime](#packet-graph-wiyth-lifetime)
* `Concurrency` [Packet_Graph_With_Hunter](#packet-graph-with-hunter)

## Topics
### Packet Graph
Create a directional, cyclic graph with some additional edges (shortcuts for bigger cycle). Send an array of packets through this graph, from `0` to `n-1` vertex. Each packet is an integer. Packet can travel to the next vertex, if it is not occupied. Each of vertices, as well as sender and reciever, are different threads, and use Go channels to communicate. 

### Packet Graph With Lifetime
The same as [Packet_Graph](#packet-graph), but a packet has a maximul lifetime, and dies if doesn't get to the end before it.

### Packet Graph With Hunter
The same as [Packet_Graph_With_Lifetime](#packet-graph-with-lifetime), but there is a hunter, that adds traps in random graph nodes, and packet dies if it gets trapped.
