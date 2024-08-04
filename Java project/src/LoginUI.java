public class LoginUI extends ConsoleUI {

    // Let a person login
    @Override
    public void run() {
        System.out.println("Login");
        System.out.println("Are you an employee, employer or admin?");
        System.out.println(" - If you are an admin, your User ID is 'admin'");
        System.out.println(" - If you are an employee or employer, enter your registered User ID and password");

        String id = readString("Enter user ID: ", true);
        String password = readString("Enter password: ", true);

        System.out.println();

        // Based on the username and password, start the appropriate UI
        if (id.equals("admin") && password.equals("admin")) {
            new AdminUI().run();
        } else {
            Person person = Database.getInstance().getPerson(id);
            
            if(person == null || !person.getPassword().equals(password)) {
                System.out.println("Error: Invalid credentials.");
            } else if(person instanceof Employee) {
                new EmployeeUI((Employee) person).run();
            } else {
                new EmployerUI((Employer) person).run();
            }
        }
    }
}
