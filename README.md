# CP_Lab_02
Concurrent Programming Lab 2 - UoM CSE

# Challenge
This problem was originally based on the Senate bus at Wellesley College. Riders come to a bus 
stop and wait for a bus. When the bus arrives, all the waiting riders invoke boardBus, but anyone who
arrives while the bus is boarding has to wait for the next bus. The capacity of the bus is 50 people; if there
are more than 50 people waiting, some will have to wait for the next bus. When all the waiting riders have
boarded, the bus can invoke depart. If the bus arrives when there are no riders, it should depart immediately.

# Screenshots

Following screenshots were added which consist of console outputs.
1. Bus with id 1 with only one rider
2. Bus with id 2 with 43 riders
3. Bud with id 3 arrives riders arriving scenario
4. Bus with id 4 departing without single rider
5. More than 50 riders waiting for a bus(es)
6. Bus with id 5 arrives
7. Bus with id 5 departs with the first 50 riders (others waiting for the next bus)
8. Bus with id 6 arrives, riders boarding where bus with id 5 left off. Riders continues to arrive at the bus holt 

# Notes
```
cd main/src
javac BusStop.java
java BusStop
```
