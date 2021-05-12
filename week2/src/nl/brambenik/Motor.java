package nl.brambenik;

public class Motor implements Vehicle{

    int speed = 100;

    @Override
    public void drive() {
        System.out.println("Vroemmm ik ga een rondje racen op mijn motor!");
    }

    @Override
    public void setSpeed(int s) {
        speed = s;
    }

    @Override
    public void getSpeed() {
        System.out.println("De motor gaat " + speed + " KM/h");
    }
}
