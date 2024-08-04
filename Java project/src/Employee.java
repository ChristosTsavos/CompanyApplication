
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

// Respresents an employee
public class Employee extends Person {

    // Additional fields
    private int yearsOfExperience;
    private String employmentRequestId;
    private String companyId;

    // Create an employee which by default is not employed
    public Employee(String id, String firstName, String lastName, Date birthDate, String password,
            int yearsOfExperience, String employmentRequestId, String companyId) {
        super(id, firstName, lastName, birthDate, password);

        this.yearsOfExperience = yearsOfExperience;
        setEmploymentRequestId(employmentRequestId);
        setCompanyId(companyId);
    }

    // Load an employee from file
    public Employee(Scanner input) throws Exception {
        super(input);
    }

    // Return a string representation of an employee
    @Override
    public String toString() {
        return super.toString() + "\n"
                + "Years of Experience: " + yearsOfExperience + "\n"
                + "Employment Request ID: " + employmentRequestId + "\n"
                + "Company ID: " + companyId;
    }

    // Update the user's password, throws an exception if
    // the password does not start with a number and below 7 characters
    @Override
    public void setPassword(String password) {
        if (password == null || password.isEmpty()
                || !Character.isDigit(password.charAt(0))
                || password.length() <= 6) {
            throw new IllegalArgumentException("Password should start with a number and have more than 6 character.");
        }

        this.password = password;
    }

    // Update the employment request ID
    public void setEmploymentRequestId(String employmentRequestId) {
        this.employmentRequestId = employmentRequestId;
    }

    // Update the company ID
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    // Access to the years of experience property
    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    // Access to the employment request id
    public String getEmploymentRequestId() {
        return employmentRequestId;
    }

    // Access to the company id
    public String getCompanyId() {
        return companyId;
    }

    // Check if employee is employed or not
    public boolean isEmployed() {
        return companyId != null;
    }

    // Read data person data from file
    @Override
    public void readFromFile(Scanner input) throws Exception {
        super.readFromFile(input);

        yearsOfExperience = Integer.parseInt(input.nextLine());
        employmentRequestId = input.nextLine();
        companyId = input.nextLine();

        if (employmentRequestId.isEmpty()) {
            employmentRequestId = null;
        }

        if (companyId.isEmpty()) {
            companyId = null;
        }
    }

    // Write the person data to an output stream
    @Override
    public void writeToFile(PrintWriter output) throws Exception {
        super.writeToFile(output);

        output.println(yearsOfExperience);

        if (employmentRequestId != null) {
            output.println(employmentRequestId);
        } else {
            output.println();
        }

        if (companyId != null) {
            output.println(companyId);
        } else {
            output.println();
        }
    }
}
