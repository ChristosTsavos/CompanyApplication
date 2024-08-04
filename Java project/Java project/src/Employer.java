import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

// Represents an employer
public class Employer extends Person {

    // Additional fields
    private String companyId;
    private String companyName;
    private String industry;
    private int numberOfEmployees;

    // Create an employer
    public Employer(String id, String firstName, String lastName, Date birthDate, String password,
            String companyId, String companyName, String industry, int numberOfEmployees) {
        super(id, firstName, lastName, birthDate, password);

        // Validate required fields
        if (companyId == null || companyId.isEmpty()
                || companyName == null || companyName.isEmpty()
                || industry == null || industry.isEmpty()) {
            throw new IllegalArgumentException("Required fields are not provided.");
        }

        this.companyId = companyId;
        this.companyName = companyName;
        this.industry = industry;
        this.numberOfEmployees = numberOfEmployees;
    }
    
    // Load employer from file
    public Employer(Scanner input) throws Exception {
        super(input);
    }

    // Update the password, it should have more than 8 characters
    @Override
    public void setPassword(String password) {
        if (password.length() <= 8) {
            throw new IllegalArgumentException("Password must contain more than 8 characters.");
        }

        this.password = password;
    }

    // Access to the company ID property
    public String getCompanyId() {
        return companyId;
    }

    // Access to the company name property
    public String getCompanyName() {
        return companyName;
    }

    // Access to the industry property
    public String getIndustry() {
        return industry;
    }

    // Access to the number of employees property
    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    // Read data person data from file
    @Override
    public void readFromFile(Scanner input) throws Exception {
        super.readFromFile(input);

        companyId = input.nextLine();
        companyName = input.nextLine();
        industry = input.nextLine();
        numberOfEmployees = Integer.parseInt(input.nextLine());
    }

    // Write the person data to an output stream
    @Override
    public void writeToFile(PrintWriter output) throws Exception {
        super.writeToFile(output);

        output.println(companyId);
        output.println(companyName);
        output.println(industry);
        output.println(numberOfEmployees);
    }
}
