package nl.brambenik;

public class Utils {

    public static void helloWorld() {
        System.out.println("Dit is mijn tweede regel Java");
    }

    public static void loopTo10() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("This is loop " + i);
        }
    }

    public static void greaterThan2(int number) {
        if (number == 2) {
            System.out.println(number + " is equal to 2.");
        } else if (number > 2) {
            System.out.println(number + " is greater than 2.");
        } else {
            System.out.println(number + " is smaller than 2.");
        }
    }

    public static void greaterThan2(String number) {
        switch (number) {
            case "zero", "one"      -> System.out.println((number + " is smaller than 2."));
            case "two"              -> System.out.println(number + " is equal to 2.");
            case "three", "four"    -> System.out.println(number + " is greater than 2.");
            default                 -> System.out.println("Bram is a lazy developer who doesn't want to add more checks.");
        }
    }

}
