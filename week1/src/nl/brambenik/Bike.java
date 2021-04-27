package nl.brambenik;

public class Bike {

    private static int speed;

    public static int getSpeed() {
        return speed;
    }

    public static void setSpeed(int valueSpeed) {
        speed = valueSpeed;
    }

    public static void drive(int power) {
        setSpeed(power * 10);
        System.out.println(getSpeed());
    }
}



