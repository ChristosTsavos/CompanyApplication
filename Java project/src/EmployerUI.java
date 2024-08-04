import java.util.HashSet;
import java.util.Set;

public class EmployerUI extends ConsoleUI {

    // Employer logged in using the UI
    private Employer employer;

    // Initialize the employer
    public EmployerUI(Employer employer) {
        this.employer = employer;
    }

    // Add a new vacancy
    private void addNewVacancy() {
        System.out.println("Creating a new vacancy...");
        String vacancyId = readString("Vacancy ID: ", true);
        String jobDescription = readString("Job Description: ", true);

        try {
            Database.getInstance().addVacancy(new Vacancy(vacancyId,
                    employer.getCompanyId(), jobDescription, true));

            System.out.println("Ok: New job vacancy added.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Display all unemployed employees
    private void listUnemployedEmployees() {
        System.out.println("Unemployed employees...");

        for (Employee employee : Database.getInstance().getEmployees()) {
            if (!employee.isEmployed()) {
                System.out.println(employee);
                System.out.println();
            }
        }
    }

    // Display all employed employees working on the employer's company
    private void listEmployeesUnderCompany() {
        System.out.println("Employees working under your company...");

        for (Employee employee : Database.getInstance().getEmployees()) {
            if (employee.isEmployed() && employee.getCompanyId().equals(employer.getCompanyId())) {
                System.out.println(employee);
                System.out.println();
            }
        }
    }

    // Return the list of vacancy IDs under the employer's company
    private Set<String> getVacanciesUnderCompany() {
        // Get all open job vacancies of this company
        Set<String> vacancyIds = new HashSet<>();

        for (Vacancy vacancy : Database.getInstance().getVacancies()) {
            if (vacancy.getCompanyId().equals(employer.getCompanyId())
                    && vacancy.isOpen()) {
                vacancyIds.add(vacancy.getId());
            }
        }

        return vacancyIds;
    }

    // Display all employees who applied on the employer's company
    private void listVacancyApplicationsUnderCompany() {
        System.out.println("People who applied for a job vacancy under your company...");

        Set<String> vacancyIds = getVacanciesUnderCompany();

        for (Employee employee : Database.getInstance().getEmployees()) {
            if (!employee.isEmployed() && employee.getEmploymentRequestId() != null
                    && vacancyIds.contains(employee.getEmploymentRequestId())) {
                System.out.println(employee);
                System.out.println();
            }
        }
    }

    // Accept an application
    private void acceptVacancyApplication() {
        System.out.println("Accepting a Job application...");
        String vacancyId = readString("Vacancy ID to process: ", true);

        // Validate the vacancy
        if (!getVacanciesUnderCompany().contains(vacancyId)) {
            System.out.println("Error: The chosen vacancy does not exist under your company or is not open.");
            return;
        }

        Vacancy vacancy = Database.getInstance().getVacancy(vacancyId);

        if (!vacancy.isOpen()) {
            System.out.println("Error: The Job vacancy is not open.");
            return;
        }

        // Validate the employee
        Person person = Database.getInstance().getPerson(readString("Employee ID to accept for vacancy: ", true));

        if (person == null || !(person instanceof Employee)) {
            System.out.println("Error: Employee does not exist.");
            return;
        }

        Employee employee = (Employee) person;

        if (employee.isEmployed() || employee.getEmploymentRequestId() == null || !employee.getEmploymentRequestId().equals(vacancyId)) {
            System.out.println("Error: Employee did not apply for the vacancy.");
            return;
        }

        employee.setCompanyId(employer.getCompanyId());
        employee.setEmploymentRequestId(null);
        vacancy.setClosed(true);

        System.out.println("Ok: Employee has been accepted for the job. The vacancy is now closed.");
    }

    // Start the employer control
    @Override
    public void run() {
        System.out.println("Welcome " + employer.getFirstName() + " " + employer.getLastName());
        System.out.println();

        while (true) {
            System.out.println("Employer Menu: ");
            System.out.println("1. Add a new vacancy");
            System.out.println("2. List all unemployed employees");
            System.out.println("3. List all employed employees under your company");
            System.out.println("4. List all persons who have applied for a vacancy in your company");
            System.out.println("5. Accept a person who applied for a vacancy in your company");
            System.out.println("0. Exit");

            int option = readInt("Option: ");

            System.out.println();

            if (option == 0) {
                break;
            }

            if (option == 1) {
                addNewVacancy();
            } else if (option == 2) {
                listUnemployedEmployees();
            } else if (option == 3) {
                listEmployeesUnderCompany();
            } else if (option == 4) {
                listVacancyApplicationsUnderCompany();
            } else if (option == 5) {
                acceptVacancyApplication();
            }

            System.out.println();
        }

        Database.getInstance().save();
    }

}
