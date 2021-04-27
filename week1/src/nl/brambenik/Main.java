package nl.brambenik;

public class Main {

    public static void main(String[] args) {
        System.out.println("Dit is mijn eerste regel Java");

        Utils.helloWorld();

        Utils.loopTo10();

        Utils.greaterThan2(3);
        Utils.greaterThan2("three");

        Bike bike = new Bike();
        Bike.setSpeed(30);
        System.out.println(Bike.getSpeed());

        Bike.drive(5);


    }
}
