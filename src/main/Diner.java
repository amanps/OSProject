package main;

public class Diner {

    private Diner mInstance;
    private int DINER_ID;
    private Thread mThread;
    private Table assignedTable;
    private Order order;

    public Diner(int id, Order order) {
        mInstance = this;
        DINER_ID = id;
        this.order = order;

        mThread = new Thread(new RunDiner());
        mThread.start();
    }

    private class RunDiner implements Runnable {

        @Override
        public void run() {
            // Have a good time.

            try {
                assignedTable = Restaurant.getRestaurant().getTableManager().seatDiner(mInstance);
                System.out.println("Diner " + mInstance.getDinerId() + " has been seated at Table " + assignedTable.getTableId());

                Restaurant.getRestaurant().getOrderManager().placeOrder(mInstance);

            } catch (InterruptedException e) {
                System.out.println("InterruptedException for Diner " + mInstance.getDinerId() + " while seating.");
                e.printStackTrace();
            }


        }
    }

    public void placeOrder(Order order) {

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
