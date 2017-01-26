package entity;


public class Train {
    private String serialNumber;
    private int capacity;

    public Train() {}

    public Train(String serialNumber, int capacity) {
        this.serialNumber = serialNumber;
        this.capacity = capacity;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return serialNumber;
    }
}
