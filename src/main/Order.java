package main;

import java.util.Random;

public class Order {

    private int ORDER_ID;
    private int burgers;
    private int fries;
    private int cokes;
    private int sundaes;

    private int startTime = 0;
    private int currentTime = 0;
    private int orderCompleteTime = 0;

    public Order(int id) {
        ORDER_ID = id;
        Random random = new Random();
        this.burgers = random.nextInt(5) + 1;
        this.fries = random.nextInt(5);
        this.cokes = random.nextInt(2);
        this.sundaes = random.nextInt(2);
    }

    public Order(int id, int burgers, int fries, int cokes, int sundaes) {
        ORDER_ID = id;
        this.burgers = burgers;
        this.fries = fries;
        this.cokes = cokes;
        this.sundaes = sundaes;
    }

    public synchronized boolean isOrderComplete() {
//        System.out.println("isOrderComplete? " + (burgers + fries + cokes + sundaes == 0));
        return burgers + fries + cokes + sundaes == 0;
    }

    public synchronized void setOrderCompleted() {
        System.out.println("Order completed. " + toString());
        orderCompleteTime = currentTime;
//        System.out.println("Notifying order completed.");
        notifyAll();
    }

    public synchronized void waitOnOrder() throws InterruptedException{
//        System.out.println("Wait on order. ");
        while (!isOrderComplete()) {
            wait();
        }
//        System.out.println("Done waiting on order. ");
    }

    public int getBurgers() {
        return burgers;
    }

    public void setBurgers(int burgers) {
        this.burgers = burgers;
    }

    public int getFries() {
        return fries;
    }

    public void setFries(int fries) {
        this.fries = fries;
    }

    public int getCokes() {
        return cokes;
    }

    public void setCokes(int cokes) {
        this.cokes = cokes;
    }

    public int getSundaes() {
        return sundaes;
    }

    public void setSundaes(int sundaes) {
        this.sundaes = sundaes;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }

    public int getOrderCompleteTime() {
        return orderCompleteTime;
    }

    public int getOrderId() {
        return ORDER_ID;
    }

    public void setStartTime(int startTime) {
        this.currentTime += startTime;
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return burgers + " Burgers, " + fries + " Fries, " + cokes + " Cokes, " + sundaes + " Sundaes";
    }
}
