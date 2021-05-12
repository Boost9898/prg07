package nl.brambenik;

public class Main {

    public static void main(String[] args) {

        Bike bike1 = new Bike();
        Car car1 = new Car();
        Motor motor1 = new Motor();

        System.out.println("--- bike1 ---");

        bike1.getSpeed();
        bike1.setSpeed(30);
        bike1.getSpeed();
        bike1.drive();

        System.out.println(" ");
        System.out.println("--- car1 ---");

        car1.getSpeed();
        car1.setSpeed(75);
        car1.getSpeed();
        car1.drive();

        System.out.println(" ");
        System.out.println("--- motor1 ---");

        motor1.getSpeed();
        motor1.setSpeed(150);
        motor1.getSpeed();
        motor1.drive();
    }

}
