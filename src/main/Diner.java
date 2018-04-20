package main;

import java.util.Random;

public class Diner {

    private Diner mInstance;
    private int DINER_ID;
    private Thread mThread;
    private Table assignedTable;
    private Order order;
    private int arrivalTime;

    public Diner(int id) {
        Random random = new Random();
        mInstance = this;
        DINER_ID = id;
        this.order = new Order();
        arrivalTime = random.nextInt(120);
        mThread = new Thread(new RunDiner());
        mThread.start();
    }

    public Diner(int id, int time, Order order) {
        mInstance = this;
        DINER_ID = id;
        this.order = order;
        arrivalTime = time;
        mThread = new Thread(new RunDiner());
        mThread.start();
    }

    private class RunDiner implements Runnable {

        @Override
        public void run() {
            try {
                // To have a good time.

                System.out.println("Diner " + mInstance.getDinerId() + " arrived at " + arrivalTime);

                assignedTable = Restaurant.getRestaurant().getTableManager().waitToBeSeated(mInstance, arrivalTime);
                System.out.println("Diner " + mInstance.getDinerId() + " has been seated at Table " +
                        assignedTable.getTableId() + " at time " + assignedTable.getTableTime());

                Restaurant.getRestaurant().getOrderManager().placeOrder(mInstance, assignedTable.getTableTime());

                waitOnOrder();

                leaveRestaurant(order.getOrderCompleteTime());

            } catch (InterruptedException e) {
                System.out.println("InterruptedException for Diner " + mInstance.getDinerId());
                e.printStackTrace();
            }
        }
    }

    public synchronized void waitOnOrder() throws InterruptedException{
        while (!order.isOrderComplete()) {
            wait();
        }
    }

    public synchronized void leaveRestaurant(int time) {
        Restaurant.getRestaurant().getTableManager().removeDinerFromTable(mInstance, time);
    }

    public int getDinerId() {
        return DINER_ID;
    }

    public Table getAssignedTable() {
        return assignedTable;
    }

    public void setAssignedTable(Table assignedTable) {
        this.assignedTable = assignedTable;
    }

    public Order getOrder() {
        return order;
    }
}
