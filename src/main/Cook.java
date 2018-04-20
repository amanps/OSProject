package main;

public class Cook {

    private int COOK_ID;
    private Thread mThread;
    private Order cookingOrder;

    public Cook(int id) {
        COOK_ID = id;
        mThread = new Thread(new RunCook());
        mThread.start();
    }

    private class RunCook implements Runnable {

        @Override
        public void run() {
            while (true) {
                while (!getCookingOrder().isOrderComplete()) {
                    // keep trying to cook something part of the order.

                }
            }
        }
    }

    public Order getCookingOrder() {
        return cookingOrder;
    }

    public Thread getThread() {
        return mThread;
    }

    public int getId() {
        return COOK_ID;
    }
}
