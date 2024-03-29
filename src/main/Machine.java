package main;

public class Machine {

    private int nextAvailableTime = 0;
    private boolean isBusy = false;
    private String name;
    private int preparationTime;
    private final Object MONITOR;

    Machine(String name, int preparationTime) {
        MONITOR = Restaurant.getRestaurant().getMachinesMonitor();
        this.name = name;
        this.preparationTime = preparationTime;
    }

    /*
        Cooks request a machine and if they're able to acquire it,
        a new time for completion is returned for the cook's order,
        because this part of the order will take requestTime + prepTime
        amount of time.
     */
    public int acquireMachine(Cook cook, int requestTime) throws InterruptedException {
        synchronized (MONITOR) {
            while (isBusy) MONITOR.wait();

            isBusy = true;

            if (requestTime >= nextAvailableTime) {
                System.out.println("Cook " + cook.getId() + " acquired " + name + " at time " + requestTime +
                        " for Order " + cook.getCookingOrder().getOrderId());
                nextAvailableTime = requestTime + preparationTime;
            } else {
                System.out.println("Cook " + cook.getId() + " acquired " + name + " at time " + nextAvailableTime +
                        " for Order " + cook.getCookingOrder().getOrderId());
                nextAvailableTime = nextAvailableTime + preparationTime;
            }

            return nextAvailableTime;
        }
    }

    public void releaseMachine() {
        synchronized (MONITOR) {
            isBusy = false;
//            System.out.println("Notifying machine release.");
            MONITOR.notifyAll();
        }
    }

    public boolean isBusy() {
        synchronized (MONITOR) {
            return isBusy;
        }
    }

}
