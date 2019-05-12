//CS3502(01) Urbanyak Assignment 4 Synchronization with Java Threads Implementation
//Producer/Consumer.java use java threads to synchronize producer/consumer processes
//The implementation uses three semaphores, one for producer (prod), one for consumer (con)
//and one for mutual exclusion

//Array list is used for the shared buffer to make adding and removing items easier

//The producer produces an item, acquires prod semaphore, acquires mutual exclusion to the buffer using mutex semaphore,
//deposits an item into the shared buffer, releases mutual exclusion
//then releases (wakes up) the con semaphore (initially set to be unavailable - value 0)

//The consumer process acquires the con semaphore, acquires mutual exclusion to the buffer using mutex semaphore,
//consumes an item by removing it from the buffer, releases mutual exclusion
//then releases the prod semaphore (to signal the producer process)

//There appears to be a pattern for item deposit/consumption
//Producer gradually fills up the shared buffer until it reaches max capacity
//the consumer then gradually empties the shared buffer until its empty or almost empty

//Using Thread.sleep within the try block of the processes can change process behavior
//using the sleep function as:
//ProdconSync.prod.acquire(); or ProdconSync.con.acquire();
//Thread.sleep(50);
//ProdconSync.mutex.acquire();
//will alternate deposit/consumption 1:1

//Reference: https://sandeepin.wordpress.com/2012/01/21/producer-consumer-problem-solution-using-semaphore/
//Used Java version 10.0.2

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class ProdconSync {
    static final int N = 100; //number of slots in buffer
    static Semaphore prod = new Semaphore(N); //producer semaphore
    static Semaphore con = new Semaphore(0); //consumer semaphore
    static Semaphore mutex = new Semaphore(1); //mutual exclusion semaphore

    public static void main(String[] args) {
        ArrayList<Character> buffer = new ArrayList<>(); //shared buffer
        Producer prod = new Producer(buffer);
        Consumer cons = new Consumer(buffer);
        prod.start();
        cons.start();
    }
}

