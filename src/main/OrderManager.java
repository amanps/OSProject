package main;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderManager {

    private Queue<Order> ordersPlaced;

    public OrderManager() {
        ordersPlaced = new ConcurrentLinkedQueue<>();
    }

    /*
        Does not need to be synchronized because the queue is thread safe.
     */
    public void placeOrder(Diner diner) {
        ordersPlaced.offer(diner.getOrder());
    }
}
