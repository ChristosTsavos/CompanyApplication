public class Main {
    
    // Entry point of the program then starts the appropriate
    // UI based on login
    public static void main(String[] args) {
        Database.getInstance();
        
        // Start users in the login user interface
        new LoginUI().run();
    }
}
