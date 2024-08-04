public class EmployeeUI extends ConsoleUI {

    // Logged in employee
    private Employee employee;

    // Initialize the employee that uses this UI
    public EmployeeUI(Employee employee) {
        this.employee = employee;
    }

    // Display all jobs that are available
    private void listAvailableJobs() {
        System.out.println("Available Jobs:");
        System.out.println();

        for (Vacancy vacancy : Database.getInstance().getVacancies()) {
            if (vacancy.isOpen()) {
                System.out.println(vacancy);
                System.out.println();
            }
        }
    }

    // Let the employee apply for a Job
    private void applyForJob() {
        System.out.println("Applying for a Job...");
        Vacancy vacancy = Database.getInstance().getVacancy(readString("Job ID: ", true));

        if (vacancy == null) {
            System.out.println("Error: The job does not exist.");
        } else if (!vacancy.isOpen()) {
            System.out.println("Error: The job is not open.");
        } else {
            employee.setEmploymentRequestId(vacancy.getId());
            System.out.println("Ok: Your application has been submitted.");
        }
    }

    // Handle employee controls
    @Override
    public void run() {
        System.out.println("Welcome " + employee.getFirstName() + " " + employee.getLastName());
        System.out.println();

        while (true) {
            System.out.println("Employee Menu: ");
            System.out.println("1. View the list of all job vacancies");
            System.out.println("2. Apply for a job vacancy");
            System.out.println("0. Exit");
            
            int option = readInt("Option: ");

            System.out.println();

            if (option == 0) {
                break;
            }

            if (option == 1) {
                listAvailableJobs();
            } else if (option == 2) {
                applyForJob();
            }

            System.out.println();
        }

        Database.getInstance().save();
    }
}
