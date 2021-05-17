package nl.brambenik;

public abstract class Vehicle implements Comparable<Vehicle> {
    public abstract void drive();
    public abstract void setSpeed(int speed);
    public abstract int getSpeed();

    @Override
    public int compareTo(Vehicle other) {
        if (this.getSpeed() > other.getSpeed()) {
        return 1;
        } else if (other.getSpeed() > this.getSpeed()) {
            return -1;
        } else {
            return 0;
        }
    }
}