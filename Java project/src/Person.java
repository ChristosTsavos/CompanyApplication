import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// Holds common properties of employee and employer
public abstract class Person implements FileReadAndWritable {

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Required fields
    private String id;
    private String firstName;
    private String lastName;
    private Date birthDate;

    // This one is protected so that it can be updated by subclasses
    protected String password;

    // Create a person
    public Person(String id, String firstName, String lastName, Date birthDate, String password) {
        // Validate the required fields
        if (id == null || id.isEmpty()
                || firstName == null || firstName.isEmpty()
                || lastName == null || lastName.isEmpty()
                || birthDate == null
                || password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Required fields are not provided.");
        }

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        setPassword(password);
    }

    // Create a person from an input stream
    public Person(Scanner input) throws Exception {
        readFromFile(input);
    }

    // Return a string reprsentation of a person
    @Override
    public String toString() {
        return "User ID: " + id + "\n"
                + "Name: " + firstName + " " + lastName + "\n"
                + "Birthdate: " + dateFormat.format(birthDate);
    }

    // Update the password, this one is abstract because
    // password validation is different between Employee and Employers
    public abstract void setPassword(String password);

    // Access to the id property
    public String getId() {
        return id;
    }

    // Access to the first name property
    public String getFirstName() {
        return firstName;
    }

    // Access to the last name property
    public String getLastName() {
        return lastName;
    }

    // Access to the birthdate property
    public Date getBirthDate() {
        return birthDate;
    }

    // Access to the password property
    public String getPassword() {
        return password;
    }

    // Read data person data from file
    @Override
    public void readFromFile(Scanner input) throws Exception {
        id = input.nextLine();
        firstName = input.nextLine();
        lastName = input.nextLine();
        birthDate = dateFormat.parse(input.nextLine());
        password = input.nextLine();
    }

    // Write the person data to an output stream
    @Override
    public void writeToFile(PrintWriter output) throws Exception {
        output.println(id);
        output.println(firstName);
        output.println(lastName);
        output.println(dateFormat.format(birthDate));
        output.println(password);
    }
}
