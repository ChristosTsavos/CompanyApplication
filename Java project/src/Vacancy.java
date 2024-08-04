import java.io.PrintWriter;
import java.util.Scanner;

// Represents a job vacancy
public class Vacancy implements FileReadAndWritable {

    private String id;
    private String companyId;
    private String jobDescription;
    private boolean open;

    // Create a vacancy
    public Vacancy(String id, String companyId, String jobDescription, boolean open) {
        this.id = id;
        this.companyId = companyId;
        this.jobDescription = jobDescription;
        this.open = open;
    }

    // Load vacancy object from file
    public Vacancy(Scanner input) throws Exception {
        readFromFile(input);
    }

    // Return a string representation of the job
    @Override
    public String toString() {
        return "Job ID: " + id + "\n"
                + "Company ID: " + companyId + "\n"
                + "Job Description: " + jobDescription + "\n"
                + "Is Open: " + open;
    }

    // Access to the ID property
    public String getId() {
        return id;
    }

    // Access to the company ID property
    public String getCompanyId() {
        return companyId;
    }

    // Access the job description property
    public String getJobDescription() {
        return jobDescription;
    }

    // Check if job is open
    public boolean isOpen() {
        return open;
    }

    // Load the vacancy from file
    @Override
    public void readFromFile(Scanner input) throws Exception {
        id = input.nextLine();
        companyId = input.nextLine();
        jobDescription = input.nextLine();
        open = Boolean.parseBoolean(input.nextLine());
    }

    // Save the vacancy to file
    @Override
    public void writeToFile(PrintWriter output) throws Exception {
        output.println(id);
        output.println(companyId);
        output.println(jobDescription);
        output.println(open);
    }

    // Mark the application closed
    public void setClosed(boolean b) {
        open = false;
    }
}
