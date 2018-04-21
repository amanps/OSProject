package main;

public class Cook {

    private int COOK_ID;
    private Cook instance;
    private Thread mThread;
    private Order cookingOrder;

    private final int BURGER = 0;
    private final int FRIES = 1;
    private final int COKE = 2;
    private final int SUNDAE = 3;

    public Cook(int id) {
        instance = this;
        COOK_ID = id;
        mThread = new Thread(new RunCook());
        mThread.setName("Cook" + id);
        mThread.start();
    }

    private class RunCook implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    waitForAnOrder();

                    while (cookingOrder != null && !cookingOrder.isOrderComplete()) {
                        // keep trying to cook something part of the order.

                        if (cookingOrder.getBurgers() > 0 &&
                                !Restaurant.getRestaurant().getBurgerMachine().isBusy()) {

                            cook(BURGER, Restaurant.getRestaurant().getBurgerMachine());

                        } else if (cookingOrder.getFries() > 0 &&
                                !Restaurant.getRestaurant().getFriesMachine().isBusy()) {
                            
                            cook(FRIES, Restaurant.getRestaurant().getFriesMachine());
                            
                        } else if (cookingOrder.getCokes() > 0 &&
                                !Restaurant.getRestaurant().getCokeMachine().isBusy()) {
                            
                            cook(COKE, Restaurant.getRestaurant().getCokeMachine());
                            
                        } else if (cookingOrder.getSundaes() > 0 &&
                                !Restaurant.getRestaurant().getSundaeMachine().isBusy()) {
                            
                            cook(SUNDAE, Restaurant.getRestaurant().getSundaeMachine());
                            
                        }
                    }

                    Restaurant.getRestaurant().getOrderManager().setOrderCompleted(cookingOrder);

                } catch (InterruptedException e) {
                    System.out.println("InterruptedException for Cook " + COOK_ID);
                    e.printStackTrace();
                }

//                if (Restaurant.getRestaurant().getOrderManager().allOrdersServed()) {
//                    System.out.println("All done.");
//                    break;
//                }
            }
        }
    }

    public synchronized void cook(int dish, Machine machine) throws InterruptedException {
        int timeAfterCooking = machine.acquireMachine(instance, cookingOrder.getCurrentTime());
        cookingOrder.setCurrentTime(timeAfterCooking);

        switch (dish) {
            case BURGER:
                cookingOrder.setBurgers(cookingOrder.getBurgers() - 1);
                break;
            case FRIES:
                cookingOrder.setFries(cookingOrder.getFries() - 1);
                break;
            case COKE:
                cookingOrder.setCokes(cookingOrder.getCokes() - 1);
                break;
            case SUNDAE:
                cookingOrder.setSundaes(cookingOrder.getSundaes() - 1);
                break;
        }
        machine.releaseMachine();
    }

    public synchronized void waitForAnOrder() throws InterruptedException {
//        System.out.println(instance.toString() + " waiting.");
        cookingOrder = Restaurant.getRestaurant().getOrderManager().getNewOrder();
//        System.out.println(instance.toString() + " done waiting.");
        System.out.println("Cook " + COOK_ID + " is assigned Order " + cookingOrder.getOrderId());
    }

    public synchronized Order getCookingOrder() {
        return cookingOrder;
    }

    public Thread getThread() {
        return mThread;
    }

    public int getId() {
        return COOK_ID;
    }

    @Override
    public String toString() {
        return "Cook " + COOK_ID;
    }
}
