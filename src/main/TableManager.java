package main;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TableManager {

    private Queue<Table> availableTables;

    public TableManager(int numOfTables) {
        System.out.println("Table Manager new");
        availableTables = new ConcurrentLinkedQueue<>();

        for (int i = 0; i < numOfTables; i++) {
            availableTables.offer(new Table(i + 1));
        }
    }

    public synchronized Table seatDiner(Diner diner) throws InterruptedException{
        while (availableTables.isEmpty()) {
            System.out.println("Diner " + diner.getDinerId() + " is waiting to be seated.");
            wait();
        }
        return availableTables.poll();
    }

    public synchronized void removeDinerFromTable(Diner diner) {
        if (diner == null)
            return;
        Table assignedTable = diner.getAssignedTable();
        if (assignedTable == null)
            return;
        System.out.println("Diner " + diner.getDinerId() + " got up from Table " + assignedTable);
        availableTables.add(assignedTable);
        diner.setAssignedTable(null);
        notifyAll();
    }
}
