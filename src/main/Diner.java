package main;

import java.util.Random;

public class Diner {

    private Diner mInstance;
    private int DINER_ID;
    private Thread mThread;
    private Table assignedTable;
    private Order order;
    private int arrivalTime;
    private int EATING_TIME = 30;

    public Diner(int id) {
        Random random = new Random();
        mInstance = this;
        DINER_ID = id;
        this.order = new Order(id);
        arrivalTime = random.nextInt(30);
        mThread = new Thread(new RunDiner());
        mThread.setName("Diner" + id);
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

                System.out.println("Diner " + mInstance.getDinerId() + " arrived at " + arrivalTime +
                        " with Order : " + order.toString());

                assignedTable = Restaurant.getRestaurant().getTableManager().waitToBeSeated(mInstance, arrivalTime);
                System.out.println("Diner " + mInstance.getDinerId() + " has been seated at Table " +
                        assignedTable.getTableId() + " at time " + assignedTable.getTableTime());
                Restaurant.getRestaurant().onDinerSeated();

                Restaurant.getRestaurant().getOrderManager().placeOrder(mInstance, assignedTable.getTableTime());

                waitOnOrder();
                System.out.println("Diner " + mInstance.getDinerId() + " got food at " + order.getOrderCompleteTime());

                int leavingTime = order.getOrderCompleteTime() + EATING_TIME;
                System.out.println("Diner " + mInstance.getDinerId() + " got up from Table " +
                        assignedTable.getTableId() + " at " + leavingTime);
                leaveRestaurant(leavingTime);

            } catch (InterruptedException e) {
                System.out.println("InterruptedException for Diner " + mInstance.getDinerId());
                e.printStackTrace();
            }
        }
    }

    public synchronized void waitOnOrder() throws InterruptedException {
        Restaurant.getRestaurant().getOrderManager().waitOnOrder(order);
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
