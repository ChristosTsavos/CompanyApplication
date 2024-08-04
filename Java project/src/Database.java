
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// Manages the people and vacancies saving, and loading of data
public class Database {

    // The only instance of database
    private static Database instance;

    private Map<String, Person> people;
    private Map<String, Vacancy> vacancies;

    // We make this class private because only one instance of this
    // will appear in the program
    private Database() {
        // Load the employee and employers from file
        load();
    }

    // Load the employees, employers, and vacancies from file
    private void load() {
        people = new HashMap<>();
        vacancies = new HashMap<>();

        try {
            // Load the employees
            Scanner inFile = new Scanner(new File("employee.txt"));

            while (inFile.hasNextLine()) {
                addPerson(new Employee(inFile));
            }

            inFile.close();
        } catch (Exception e) {
            System.out.println("employee.txt not found, starting a new one...");
        }

        try {
            // Load the employers
            Scanner inFile = new Scanner(new File("employer.txt"));

            while (inFile.hasNextLine()) {
                addPerson(new Employer(inFile));
            }

            inFile.close();
        } catch (Exception e) {
            System.out.println("employer.txt not found, starting a new one...");
        }

        try {
            // Load the vacancies
            Scanner inFile = new Scanner(new File("vacancies.txt"));

            while (inFile.hasNextLine()) {
                addVacancy(new Vacancy(inFile));
            }

            inFile.close();
        } catch (Exception e) {
            System.out.println("vacancies.txt not found, starting a new one...");
        }
    }

    // Save the data to text files
    public void save() {
        try {
            // Save the employees and employers
            PrintWriter employeeOutFile = new PrintWriter(new File("employee.txt"));
            PrintWriter employerOutFile = new PrintWriter(new File("employer.txt"));

            for (Person person : people.values()) {
                if (person instanceof Employee) {
                    person.writeToFile(employeeOutFile);
                } else if (person instanceof Employer) {
                    person.writeToFile(employerOutFile);
                }
            }

            employeeOutFile.close();
            employerOutFile.close();

            // Save the vacancies
            PrintWriter outFile = new PrintWriter(new File("vacancies.txt"));

            for (Vacancy vacancy : vacancies.values()) {
                vacancy.writeToFile(outFile);
            }

            outFile.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            System.exit(0);
        }
    }

    // Add a new vacancy to the database
    public void addVacancy(Vacancy vacancy) {
        if (vacancies.containsKey(vacancy.getId())) {
            throw new IllegalArgumentException("Vacancy ID is already used.");
        }

        vacancies.put(vacancy.getId(), vacancy);
    }

    // Add a new person to the database
    public void addPerson(Person person) {
        if (people.containsKey(person.getId())
                || person.getId().equals("admin")) {
            throw new IllegalArgumentException("The user ID is already used.");
        }

        people.put(person.getId(), person);
    }

    // Return the list of employees
    public List<Employee> getEmployees() {
        List<Employee> employees = new ArrayList<>();

        for (Person person : people.values()) {
            if (person instanceof Employee) {
                employees.add((Employee) person);
            }
        }

        return employees;
    }

    // Get the person with the given ID
    public Person getPerson(String id) {
        if (people.containsKey(id)) {
            return people.get(id);
        }

        return null;
    }

    // Return all job vacancies
    public List<Vacancy> getVacancies() {
        return new ArrayList<>(vacancies.values());
    }

    // Find and return the vacancy that matches the ID
    public Vacancy getVacancy(String id) {
        if (vacancies.containsKey(id)) {
            return vacancies.get(id);
        }

        return null;
    }

    // Get the company name of a company ID
    public String getCompanyName(String companyId) {
        for (Person person : people.values()) {
            if (person instanceof Employer) {
                Employer employer = (Employer) person;

                if (employer.getCompanyId().equals(companyId)) {
                    return employer.getCompanyName();
                }
            }
        }

        return null;
    }
    
    // Get the ID of a company given name
    public String getCompanyId(String companyName) {
        for (Person person : people.values()) {
            if (person instanceof Employer) {
                Employer employer = (Employer) person;

                if (employer.getCompanyName().equalsIgnoreCase(companyName)) {
                    return employer.getCompanyId();
                }
            }
        }
        
        return null;
    }

    // Access only the one and only one database object
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }
}
