package nl.brambenik;

public class Car extends Vehicle {

    int speed = 50;

    @Override
    public void drive() {
        System.out.println("Skkkkrt we gaan een rondje rijden!");
    }

    @Override
    public void setSpeed(int s) {
        speed = s;
    }

    @Override
    public int getSpeed() {
        return speed;
    }
}
