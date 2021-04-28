package nl.brambenik;

public class Main {

    public static void main(String[] args) {
        System.out.println("Dit is mijn eerste regel Java");

        Utils.helloWorld();

        Utils.loopTo10();

        Utils.greaterThan2(3);
        Utils.greaterThan2("three");

        Bike.setSpeed(30);
        System.out.println("Bike speed is " + Bike.getSpeed());

        Bike.drive(5);

        System.out.println("Persons " + Tandem.getPersons());

        System.out.println("BatteryLevel " + Ebike.getBatteryLevel() + "%");

        Tandem.setPersons(1);
        Tandem.accelerate(1);
        Tandem.setPersons(2);
        Tandem.accelerate(1);

    }
}
