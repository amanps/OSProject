package main;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderManager {

    private Queue<Order> ordersPlaced;

    public OrderManager() {
        ordersPlaced = new ConcurrentLinkedQueue<>();
    }

    public synchronized void placeOrder(Diner diner, int time) {
        System.out.println("Diner " + diner.getDinerId() + " placed an order at " + time);
        ordersPlaced.offer(diner.getOrder());
        notifyAll();
    }
}
