package main;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class OrderManager {

    private Queue<Order> ordersPlaced;
    private int orders;
    private int ordersServed;

    public OrderManager(int orders) {
        ordersPlaced = new ConcurrentLinkedQueue<>();
        this.orders = orders;
    }

    public synchronized void placeOrder(Diner diner, int time) {
        System.out.println("Diner " + diner.getDinerId() + " placed an order at " + time);
        diner.getOrder().setStartTime(time);
        ordersPlaced.offer(diner.getOrder());
//        System.out.println("Notifying placing of order.");
        notifyAll();
    }

    public synchronized void waitOnOrder(Order order) throws InterruptedException {
        while (!order.isOrderComplete()) {
            wait();
        }
    }

    public synchronized void setOrderCompleted(Order order) {
//        System.out.println("Order completed. " + toString());
        ordersServed += 1;
        order.setOrderCompleted();
        notifyAll();
//        System.out.println("Notifying order completed.");
    }

    public synchronized Order getNewOrder() throws InterruptedException{
        while (isQueueEmpty()) {
            wait();
        }
        return ordersPlaced.poll();
    }

    public synchronized boolean isQueueEmpty() {
//        System.out.println("isQueueEmpty");
        return ordersPlaced.isEmpty();
    }

    public synchronized boolean allOrdersServed() {
        return ordersServed == orders;
    }
}
