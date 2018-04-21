package main;

import java.util.ArrayList;

public class Restaurant {

    private static Restaurant mInstance;

    private int diners;
    private int cooks;
    private int tables;

    private Machine burgerMachine;
    private Machine friesMachine;
    private Machine cokeMachine;
    private Machine sundaeMachine;

    private ArrayList<Cook> cooksList = new ArrayList<>();
    private ArrayList<Diner> dinersList = new ArrayList<>();

    private TableManager mTableManager;
    private OrderManager mOrderManager;

    public static synchronized Restaurant getRestaurant() {
        if (mInstance == null){
            synchronized (Restaurant.class) {
                if (mInstance == null) {
                    mInstance = new Restaurant();
                }
            }
        }
        return mInstance;
    }

    private Restaurant() {
        mInstance = this;
        diners = 2;
        cooks = 2;
        tables = 1;

        mTableManager = new TableManager(tables);
        mOrderManager = new OrderManager();

        burgerMachine = new Machine("Burger Machine", 5);
        friesMachine = new Machine("Fries Machine", 3);
        cokeMachine = new Machine("Coke Machine", 2);
        sundaeMachine = new Machine("Sundae Machine", 1);

//        dinersList.add(new Diner(1, 0, new Order(1, 1, 0, 0, 0)));

        Thread restaurantThread = new Thread(new RunRestaurant());
        restaurantThread.setName("RestaurantThread");
        restaurantThread.start();
    }

    private class RunRestaurant implements Runnable {

        @Override
        public void run() {
            dinersList.add(new Diner(1, 0, new Order(1, 1, 0, 2, 0)));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            dinersList.add(new Diner(2, 2, new Order(2, 0, 1, 0, 1)));
//            for (int i = 0; i < diners; i++) {
//                dinersList.add(new Diner(i + 1));
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

            for (int i = 0; i < cooks; i++) {
                cooksList.add(new Cook(i + 1));
            }
        }
    }

    public static void main(String[] args) {
        getRestaurant();
    }

    public TableManager getTableManager() {
        return mTableManager;
    }

    public OrderManager getOrderManager() {
        return mOrderManager;
    }

    public Machine getBurgerMachine() {
        return burgerMachine;
    }

    public Machine getFriesMachine() {
        return friesMachine;
    }

    public Machine getCokeMachine() {
        return cokeMachine;
    }

    public Machine getSundaeMachine() {
        return sundaeMachine;
    }
}
