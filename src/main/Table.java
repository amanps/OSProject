package main;

public class Table {

    private int TABLE_ID;
    private int nextAvailableTime;

    public Table(int id) {
        TABLE_ID = id;
    }

    public synchronized int getTableId() {
        return TABLE_ID;
    }

    public synchronized int getTableTime() {
        return nextAvailableTime;
    }

    public void setTableTime(int time) {
        this.nextAvailableTime = time;
    }
}
