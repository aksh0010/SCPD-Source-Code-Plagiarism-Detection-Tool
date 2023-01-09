import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

  public static void main(String args[]) {
    try {
      //creating file1 for the first file we need to create signature.
      File file1 = new File(
        "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\Temp.c"
      );

      // opening the file1 stream for reading
      FileInputStream fis = new FileInputStream(file1); //opens a connection to an actual file

      // byte[] to read data file in to byte array
      byte bytearray[] = new byte[fis.available()];
      fis.read(bytearray);

      // get the string value from byte []
      String words = new String(bytearray);

      // Stringtokenizer object to token the string words with param delimeters

      StringTokenizer tokenizer = new StringTokenizer(
        words,
        ",.<>/?;:'\"`~[]{}\\|!@#$%^&*()-+_="
      );

      // System.out.println(tokenizer.hasMoreTokens());

      // here we need to ignore the junk words that is tab spaces and new line characters.
      while (tokenizer.hasMoreTokens()) {
        if (
          tokenizer.nextElement().equals("\t") ||
          tokenizer.nextElement().equals("\n") ||
          tokenizer.nextElement().equals(" ")
        ) {
          continue;
        }
        System.out.println(tokenizer.nextToken());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
