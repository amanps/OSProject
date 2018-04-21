package main;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TableManager {

    private Queue<Table> availableTables;

    public TableManager(int numOfTables) {
        availableTables = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < numOfTables; i++) {
            availableTables.offer(new Table(i + 1));
        }
    }

    public synchronized Table waitToBeSeated(Diner diner, int arrivalTime) throws InterruptedException{
        while (availableTables.isEmpty()) {
            System.out.println("Diner " + diner.getDinerId() + " is waiting to be seated.");
            wait();
        }
        Table seat = availableTables.poll();
        if (arrivalTime >= seat.getTableTime()) {
            seat.setTableTime(arrivalTime);
        }
        seat.setTableTime(arrivalTime);
        return seat;
    }

    public synchronized void removeDinerFromTable(Diner diner, int time) {
        if (diner == null)
            return;
        Table assignedTable = diner.getAssignedTable();
        if (assignedTable == null)
            return;
        availableTables.add(assignedTable);
        diner.setAssignedTable(null);
//        System.out.println("Notifying remove diner from table.");
        notifyAll();
    }
}
