
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// User interface that has utiliy function for text-based output
public abstract class ConsoleUI {

    private Scanner in = new Scanner(System.in);

    // Forces the user to enter a string value
    public String readString(String prompt, boolean required) {
        while (true) {
            System.out.print(prompt);
            String value = in.nextLine().trim();

            if (!required) {
                return value;
            }

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Error: A value is required.");
        }
    }

    // Forces the user to enter an integer value
    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readString(prompt, true));
            } catch (Exception e) {
            }

            System.out.println("Error: An integer value is required.");
        }
    }

    // Asks a yes/no question
    public boolean confirm(String prompt) {
        while (true) {
            String value = readString(prompt + " (y/n) ", true);

            if (value.equalsIgnoreCase("y")) {
                return true;
            }

            if (value.equalsIgnoreCase("n")) {
                return false;
            }

            System.out.println("Error: Invalid response.");
        }
    }

    // Forces the user to enter a date
    public Date readDate(String prompt) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        while (true) {
            try {
                return sdf.parse(readString(prompt + " (yyyy-mm-dd): ", true));
            } catch (Exception e) {
            }

            System.out.println("Error: Invalid date.");
        }
    }

    // To be implemented by subclasses
    public abstract void run();
}
