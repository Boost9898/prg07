package nl.brambenik;

public class Car implements Vehicle {

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
    public void getSpeed() {
        System.out.println("Speed is " + speed + " KM/h");
    }
}
