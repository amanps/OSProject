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
        diner.getOrder().setStartTime(time);
        ordersPlaced.offer(diner.getOrder());
//        System.out.println("Notifying placing of order.");
        notifyAll();
    }

    public synchronized Order getNewOrder() throws InterruptedException{
        while (ordersPlaced.isEmpty()) {
            wait();
        }
        return ordersPlaced.poll();
    }

    public synchronized boolean allOrdersServed() {
        return ordersPlaced.isEmpty();
    }
}
