package nl.brambenik;

public class Bike implements Vehicle {

    int speed = 20;

    @Override
    public void drive() {
        System.out.println("Tringgg we gaan een rondje fietsen.");
    }

    @Override
    public void setSpeed(int s) {
        speed = s;
    }

    @Override
    public void getSpeed() {
        System.out.println("De fiets heeft een snelheid van " + speed + " KM/h");
    }
}
