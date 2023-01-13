import java.io.*;
// import java.util.Collections;
import java.util.HashMap;
// import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

  public static HashMap<Integer, String> fingerprintHashMap1 = new HashMap<>();
  public static HashMap<Integer, String> fingerprintHashMap2 = new HashMap<>();
  public static int common_tokens = 0;
  public static int no_of_tokens = 0; // Declaring this variable bcoz two maps could have different no of tokens so we need to choose a highest no of tokens to match and give percentage

  public static void fingerprint_comparison(
    HashMap<Integer, String> map1,
    HashMap<Integer, String> map2
  ) {
    if (map1.equals(map2)) {
      System.out.println("Files are 100% identical");
      return;
    } else {
      /**Taking maximum no of tokens from both maps for percentage
       *
       */
      if (map1.values().size() > map2.values().size()) {
        no_of_tokens = map1.values().size();
      } else {
        no_of_tokens = map2.values().size();
      }

      for (String iterable_element : map1.values()) {
        if (map2.values().contains(iterable_element)) {
          common_tokens++;
        }
      }
    }

    System.out.println(
      "Files are " +
      (float) (common_tokens * 100 / no_of_tokens) +
      " % identical to each other\n\nNumber of common tokens among files are " +
      common_tokens +
      "/" +
      no_of_tokens
    );
    // System.out.println(map1.equals(map2));
    // map1.equals(map2);
    return;
  }

  // !! ________________________________________________________________________________________________________
  // !! ________________________________________________________________________________________________________
  public static void main(String args[]) {
    try {
      //   creating file for the first file we need to create signature.
      File file1 = new File(
        "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD\\Temp.c"
      );
      //   creating file for the Second file we need to create signature.
      File file2 = new File(
        "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD\\Temp2.c"
      );
      fingerprintHashMap1 = Create_token(file1);

      fingerprintHashMap2 = Create_token(file2);

      fingerprint_comparison(fingerprintHashMap1, fingerprintHashMap2);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // !! ________________________________________________________________________________________________________
  // !! ________________________________________________________________________________________________________

  /*
   * Method Create Token takes in a file and creteas a token
   * removing all whitespaces and stores them
   * in hashmap and returns it
   *
   */
  public static HashMap<Integer, String> Create_token(File file)
    throws IOException {
    final HashMap<Integer, String> Hash_map = new HashMap<>();

    //  opening the file stream for reading
    FileInputStream fis = new FileInputStream(file); //opens a connection to an actual file

    //  byte[] to read data file in to byte array
    byte bytearray[] = new byte[fis.available()];
    fis.read(bytearray);

    //  get the string value from byte []
    String words = new String(bytearray);

    // Need to get ride of junk words that is tab spaces and new line characters.
    words = words.replaceAll("\\s+", "");

    //  Stringtokenizer object to token the string words with param delimeters
    String syntax_for_c_language = ",.<>/?;:'\"`~[]{}\\|!@#$%^&*()-+_=";

    StringTokenizer tokenizer = new StringTokenizer(
      words,
      syntax_for_c_language
    );

    System.out.println("No of tokens in the file : " + tokenizer.countTokens());
    int i = 1;

    while (tokenizer.hasMoreTokens()) {
      Hash_map.put(i, tokenizer.nextToken()); // populating hashmap with keys set in asceding order from 1
      i++;
    }
    System.out.println("\n" + Hash_map);
    System.out.println("_______________________________________________");
    fis.close();

    return Hash_map;
  }
}
