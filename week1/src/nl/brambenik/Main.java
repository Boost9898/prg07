package nl.brambenik;

public class Main {

    public static void main(String[] args) {
        System.out.println("Dit is mijn eerste regel Java");

        System.out.println("--- Hello workld ---");
        Utils.helloWorld();

        System.out.println("--- Loop to 10 ---");
        Utils.loopTo10();

        System.out.println("--- Greater than ---");
        Utils.greaterThan2(3);
        Utils.greaterThan2("three");

        System.out.println("--- Bike ---");
        Bike.setSpeed(30);
        System.out.println("Bike speed is " + Bike.getSpeed());

        Bike.drive(5);

        System.out.println("--- Bike ---");
        System.out.println("Persons: " + Tandem.getPersons());

        System.out.println("--- Battery level ---");
        System.out.println("BatteryLevel: " + Ebike.getBatteryLevel() + "%");

        System.out.println("--- Tandem ---");
        Tandem.setPersons(1);
        Tandem.accelerate(1);
        Tandem.setPersons(2);
        Tandem.accelerate(1);

        System.out.println("--- Ebike ---");
        Ebike.accelerate(1);
        Ebike.setBatteryLevel(0);
        Ebike.accelerate(1);
    }
}
