package nl.brambenik;

public class Tandem extends Bike {

    private static int persons = 1;

    public static int getPersons() {
        return persons;
    }

    public static void setPersons(int persons) {
        Tandem.persons = persons;
    }

    public static void accelerate(int power) {
        if (getPersons() >= 2) {
            drive(power * getPersons());
        } else {
            drive(power);
        }
    }

}

