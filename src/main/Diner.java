package main;

public class Diner {

    private Diner mInstance;
    private int DINER_ID;
    private Thread mThread;
    private Table assignedTable;

    public Diner(int id) {
        mInstance = this;
        DINER_ID = id;
        mThread = new Thread(new RunDiner());
        mThread.start();
    }

    private class RunDiner implements Runnable {

        @Override
        public void run() {
            // Have a good time.

            // Seating.
            try {
                assignedTable = Restaurant.getRestaurant().getTableManager().seatDiner(mInstance);
                System.out.println("Diner " + mInstance.getDinerId() + " has been seated at Table " + assignedTable.getTableId());
            } catch (InterruptedException e) {
                System.out.println("InterruptedException for Diner " + mInstance.getDinerId() + " while seating.");
                e.printStackTrace();
            }
        }
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
}
