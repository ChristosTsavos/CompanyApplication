
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AdminUI extends ConsoleUI {

    // Add a new employee
    private void addNewEmployee() {
        System.out.println("Creating a new employee...");
        System.out.println("Those marked with '*' are required fields.");
        System.out.println();

        String id = readString("*User ID: ", true);
        String firstName = readString("*First Name: ", true);
        String lastName = readString("*Last Name: ", true);
        Date birthDate = readDate("*Birth Date: ");
        String password = readString("*Password: ", true);
        int yearsOfExperience = readInt("*Years of Experience: ");
        String employmentRequestId = readString("Employement Request ID: ", false);
        String companyId = readString("Company ID: ", false);

        System.out.println();

        try {
            Database database = Database.getInstance();
            database.addPerson(new Employee(id, firstName, lastName,
                    birthDate, password, yearsOfExperience,
                    employmentRequestId, companyId));

            System.out.println("Ok: Employee added to the database.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Add a new employer
    private void addNewEmployer() {
        System.out.println("Creating a new employer...");
        System.out.println("Those marked with '*' are required fields.");
        System.out.println();

        String id = readString("*User ID: ", true);
        String firstName = readString("*First Name: ", true);
        String lastName = readString("*Last Name: ", true);
        Date birthDate = readDate("*Birth Date: ");
        String password = readString("*Password: ", true);
        String companyId = readString("*Company ID: ", true);
        String companyName = readString("*Company Name: ", true);
        int numberOfEmployees = readInt("*Number of Employees: ");
        String industry = readString("*Industry: ", true);

        System.out.println();

        try {
            Database database = Database.getInstance();
            database.addPerson(new Employer(id, firstName, lastName,
                    birthDate, password, companyId, companyName,
                    industry, numberOfEmployees));

            System.out.println("Ok: Employer added to the database.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Find the company with the most job vacancies
    private void displayCompanyWithMostJobs() {
        // Count the jobs posted for each company
        Map<String, Integer> jobsCount = new HashMap<>();

        for (Vacancy vacancy : Database.getInstance().getVacancies()) {
            if (jobsCount.containsKey(vacancy.getCompanyId())) {
                jobsCount.put(vacancy.getCompanyId(), jobsCount.get(vacancy.getCompanyId()) + 1);
            } else {
                jobsCount.put(vacancy.getCompanyId(), 1);
            }
        }

        // Find the highest count
        int highestCount = 0;

        for (Integer count : jobsCount.values()) {
            if (count > highestCount) {
                highestCount = count;
            }
        }

        // Find those company with highest count
        System.out.println("Company(ies) who has posted more jobs...");

        for (Map.Entry<String, Integer> entry : jobsCount.entrySet()) {
            if (entry.getValue() == highestCount) {
                String companyName = Database.getInstance().getCompanyName(entry.getKey());
                System.out.println(companyName);
            }
        }
    }

    // List employee of a given company
    private void listCompanyEmployees() {
        System.out.println("Listing employees of a company...");
        String companyId = Database.getInstance().getCompanyId(readString("Company Name: ", true));

        System.out.println();
        
        if (companyId == null) {
            System.out.println("Error: Company does not exist.");
            return;
        }
        
        for(Employee employee : Database.getInstance().getEmployees()) {
            if(employee.getCompanyId() != null && employee.getCompanyId().equals(companyId)) {
                System.out.println(employee);
                System.out.println();
            }
        }
    }

    // Start the administration user interface
    @Override
    public void run() {
        System.out.println("Welcome Administrator.");
        System.out.println();

        while (true) {
            System.out.println("Admin Menu: ");
            System.out.println("1. Add a new employee");
            System.out.println("2. Add a new employer");
            System.out.println("3. Display company with the most job vacancies");
            System.out.println("4. List employees of a company");
            System.out.println("0. Exit");

            int option = readInt("Option: ");

            System.out.println();

            if (option == 0) {
                break;
            }

            if (option == 1) {
                addNewEmployee();
            } else if (option == 2) {
                addNewEmployer();
            } else if (option == 3) {
                displayCompanyWithMostJobs();
            } else if (option == 4) {
                listCompanyEmployees();
            }

            System.out.println();
        }

        Database.getInstance().save();
    }

}
