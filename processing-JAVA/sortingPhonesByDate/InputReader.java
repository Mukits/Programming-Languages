import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

class InputReader {

  public static final Scanner getScanner(String file) {
    try {
      return new Scanner(new File(file));
    } 
    catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
      return null;
    }
  }
}
