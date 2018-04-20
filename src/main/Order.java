package main;

public class Order {

    private int burgers;
    private int fries;
    private int cokes;
    private int sundaes;

    private int currentProcessedTime = 0;

    public Order(int burgers, int fries, int cokes, int sundaes) {
        this.burgers = burgers;
        this.fries = fries;
        this.cokes = cokes;
        this.sundaes = sundaes;
    }

    public synchronized boolean isOrderComplete() {
        return getBurgers() + getFries() + getCokes() + getSundaes() == 0;
    }

    private int getBurgers() {
        return burgers;
    }

    public void setBurgers(int burgers) {
        this.burgers = burgers;
    }

    private int getFries() {
        return fries;
    }

    public void setFries(int fries) {
        this.fries = fries;
    }

    private int getCokes() {
        return cokes;
    }

    public void setCokes(int cokes) {
        this.cokes = cokes;
    }

    private int getSundaes() {
        return sundaes;
    }

    public void setSundaes(int sundaes) {
        this.sundaes = sundaes;
    }

    private int getCurrentProcessedTime() {
        return currentProcessedTime;
    }

    public void setCurrentProcessedTime(int currentProcessedTime) {
        this.currentProcessedTime = currentProcessedTime;
    }
}
