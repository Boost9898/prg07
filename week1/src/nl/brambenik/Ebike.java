package nl.brambenik;

public class Ebike extends Bike {

    private static int batteryLevel = 100;

    public static int getBatteryLevel() {
        return batteryLevel;
    }

    public static void setBatteryLevel(int batteryLevel) {
        Ebike.batteryLevel = batteryLevel;
    }

    public static void accelerate(int power) {
        if (batteryLevel > 1 && batteryLevel <= 100) {
            drive(power * 2);
        } else {
            drive(power);
        }
    }
}
