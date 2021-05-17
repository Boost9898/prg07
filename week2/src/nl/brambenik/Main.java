package nl.brambenik;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {

        Bike bike1 = new Bike();
        Car car1 = new Car();
        Motor motor1 = new Motor();

        System.out.println("--- bike1 ---");

        System.out.println("Snelheid van bike is " + bike1.getSpeed());
        bike1.setSpeed(30);
        System.out.println("Snelheid van bike is " + bike1.getSpeed());
        bike1.drive();

        System.out.println(" ");
        System.out.println("--- car1 ---");

        System.out.println("Snelheid van car is " + car1.getSpeed());
        car1.setSpeed(75);
        System.out.println("Snelheid van car is " + car1.getSpeed());
        car1.drive();

        System.out.println(" ");
        System.out.println("--- motor1 ---");

        System.out.println("Snelheid van motor is " + motor1.getSpeed());
        motor1.setSpeed(150);
        System.out.println("Snelheid van motor is " + motor1.getSpeed());
        motor1.drive();

        System.out.println(" ");
        System.out.println("--- array list ---");

        ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();

        Vehicle bike2 = new Bike();
        Vehicle car2 = new Car();
        Vehicle motor2 = new Motor();

        bike2.setSpeed(10);
        car2.setSpeed(20);
        motor2.setSpeed(30);

        vehicleList.add(bike2);
        vehicleList.add(car2);
        vehicleList.add(motor2);

        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle.getSpeed());
            vehicle.drive();
        }

//        Collection.sort(vehicleList);

    }

}