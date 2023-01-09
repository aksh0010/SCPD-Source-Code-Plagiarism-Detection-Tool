import java.io.*;
// import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {
  
  public static void main(String args[]) {
    try {
      // !! creating file1 for the first file we need to create signature.
      File file1 = new File(
        "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD\\Temp.c"
      );

      // !! opening the file1 stream for reading
      FileInputStream fis = new FileInputStream(file1); //opens a connection to an actual file

      // !! byte[] to read data file in to byte array
      byte bytearray[] = new byte[fis.available()];
      fis.read(bytearray);

      //!!  get the string value from byte []
      String words = new String(bytearray);

      //!! Need to get ride of junk words that is tab spaces and new line characters.

      // words = words.replace("\r\n", "").replace("\n", "");
      words = words.replaceAll("\\s+", "");

      // !! Stringtokenizer object to token the string words with param delimeters

      String syntax = ",.<>/?;:'\"`~[]{}\\|!@#$%^&*()-+_=";
      StringTokenizer tokenizer = new StringTokenizer(words, syntax);

      // System.out.println(tokenizer.hasMoreTokens());

      System.out.println(tokenizer.countTokens());
      int i = 1;

      while (tokenizer.hasMoreTokens()) {
        System.out.println(i + ")" + tokenizer.nextToken());
        i++;
      }












      fis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
