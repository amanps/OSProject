package main;

import java.util.ArrayList;

public class Restaurant {

    private static Object mutex = new Object();

    private static Restaurant mInstance;

    private Machine burgerMachine;
    private Machine friesMachine;
    private Machine cokeMachine;
    private Machine sundaeMachine;

    private ArrayList<Cook> cooksList;
    private ArrayList<Diner> dinersList = new ArrayList<>();

    private TableManager mTableManager;

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
        Thread restaurantThread = new Thread(new RunRestaurant());
        restaurantThread.start();
    }

    private class RunRestaurant implements Runnable {

        @Override
        public void run() {
            int diners = 10;
            int cooks = 2;
            int tables = 4;

            mTableManager = new TableManager(tables);

            for (int i = 0; i < diners; i++) {
                dinersList.add(new Diner(i + 1));
            }

            burgerMachine = new Machine("Burger Machine", 5);
            friesMachine = new Machine("Fries Machine", 3);
            cokeMachine = new Machine("Coke Machine", 2);
            sundaeMachine = new Machine("Sundae Machine", 1);

            /*
            for (int i = 0; i < cooks; i++) {
                cooksList.add(new Cook(i + 1));
            }
            */
        }
    }

    public static void main(String[] args) {
        getRestaurant();
    }

    public TableManager getTableManager() {
        return mTableManager;
    }
}
