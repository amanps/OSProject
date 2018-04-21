package main;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

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

    private final Object machinesMonitor = new Object();

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

        Thread restaurantThread = new Thread(new RunRestaurant());
        restaurantThread.setName("RestaurantThread");
        restaurantThread.start();
    }

    private class RunRestaurant implements Runnable {

        @Override
        public void run() {
            String filename = "test.txt";
            if (!filename.endsWith(".txt")) {
                System.out.println("Test file provided is not of the .txt format.");
                System.exit(1);
            }

            File testFile = new File(filename);
            try {
                Scanner scanner = new Scanner(testFile);
                getRestaurant().diners = Integer.parseInt(scanner.nextLine().trim());
                getRestaurant().tables = Integer.parseInt(scanner.nextLine().trim());
                getRestaurant().cooks = Integer.parseInt(scanner.nextLine().trim());

                System.out.println("Restaurant open : " + getRestaurant().diners + " " + getRestaurant().tables + " " + getRestaurant().cooks);

                for (int i = 0; i < getRestaurant().cooks; i++) {
                    getRestaurant().cooksList.add(new Cook(i + 1));
                }

                for (int i = 0; i < getRestaurant().diners; i++) {
                    if (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        line = line.replaceAll("[^0-9]+", " ");

                        String[] lineArray = line.split(" ");
                        int inTime = Integer.parseInt(lineArray[0]);
                        int burgers = Integer.parseInt(lineArray[1]);
                        int fries = Integer.parseInt(lineArray[2]);
                        int cokes = Integer.parseInt(lineArray[3]);
                        int sundaes = Integer.parseInt(lineArray[4]);
                        getRestaurant().dinersList.add(new Diner(i + 1, inTime,
                                new Order(i + 1, burgers, fries, cokes, sundaes)));
                        Thread.sleep(1000);
                    }
                }

            } catch (Exception e) {
                System.out.println("Exception in main.");
                e.printStackTrace();
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

    public Object getMachinesMonitor() {
        return machinesMonitor;
    }
}
