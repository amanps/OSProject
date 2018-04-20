package main;

import main.Cook;

public class Machine {

    private int nextAvailableTime = 0;
    private boolean isBusy = false;
    private String name;
    private int preparationTime;

    Machine(String name, int preparationTime) {
        this.name = name;
        this.preparationTime = preparationTime;
    }

    /*
        Cooks request a machine and if they're able to acquire it,
        a new time for completion is returned for the cook's order,
        because this part of the order will take requestTime + prepTime
        amount of time.
     */
    public synchronized int acquireMachine(Cook cook, int requestTime) throws InterruptedException {
        while (isBusy) wait();

        System.out.println("main.Cook " + cook.getId() + " acquired " + name);

        if (requestTime >= nextAvailableTime) {
            nextAvailableTime = requestTime + preparationTime;
        } else {
            nextAvailableTime = nextAvailableTime + preparationTime;
        }

        return nextAvailableTime;
    }

}
