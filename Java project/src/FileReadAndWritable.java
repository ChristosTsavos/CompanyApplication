import java.io.PrintWriter;
import java.util.Scanner;


// Makes an object allow to write or load to and from an output file
public interface FileReadAndWritable {
    
    // Method to be implemented by implementing classes to read
    // data from a file
    public void readFromFile(Scanner input) throws Exception;
    
    // Method to be implemented by implementing classes to write
    // data to a file
    public void writeToFile(PrintWriter output) throws Exception;
}
