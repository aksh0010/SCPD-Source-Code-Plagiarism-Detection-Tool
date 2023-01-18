import java.io.*;
// import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
// import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

  public static HashMap<String, Integer> fingerprintHashMap1 = new HashMap<>();
  public static HashMap<String, Integer> fingerprintHashMap2 = new HashMap<>();
  public static int common_tokens = 0;
  public static int no_of_tokens = 0; // Declaring this variable bcoz two maps could have different no of tokens so we need to choose a highest no of tokens to match and give percentage

  public static void fingerprint_comparison(
    HashMap<String, Integer> map1,
    HashMap<String, Integer> map2
  ) {
    // !! Checking set comaprison for subsets

    Set<String> Keyset1 = new HashSet<String>();
    Set<String> Keyset2 = new HashSet<String>();

    Keyset1.addAll(map1.keySet());
    Keyset2.addAll(map2.keySet());

    // System.out.println(
    //   "\n\n Keyset 1 size =" + Keyset1.size() + " |" + Keyset1
    // );

    // System.out.println(
    //   "\n\n Keyset 2 size = " + Keyset2.size() + "|" + Keyset2
    // );

    if (map1.equals(map2)) {
      System.out.println("Files are 100% identical");
      return;
    } else if (Keyset1.containsAll(Keyset2)) {
      System.out.println("\n\nFile 2 is a subset of File 1");
      return;
    } else if (Keyset2.containsAll(Keyset1)) {
      System.out.println("\n\nFile 1 is a subset of File 2");
      return;
    } else {
      /**Taking maximum no of tokens from
       * both maps for percentage
       *
       */
      if (map1.keySet().size() > map2.keySet().size()) {
        no_of_tokens = map1.keySet().size();
      } else {
        no_of_tokens = map2.keySet().size();
      }

      for (String iterable_element : map1.keySet()) {
        if (map2.keySet().contains(iterable_element)) {
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
  public static HashMap<String, Integer> Create_token(File file)
    throws IOException {
    final HashMap<String, Integer> Hash_map = new HashMap<>();

    //  opening the file stream for reading
    FileInputStream fis = new FileInputStream(file); //opens a connection to an actual file

    //  byte[] to read data file in to byte array
    byte bytearray[] = new byte[fis.available()];
    fis.read(bytearray);

    //  get the string value from byte []
    String words = new String(bytearray);

    // !!  Need to get ride of junk words that is tab spaces and new line characters and replacing it with single space.
    words = words.replaceAll("\\s+", " ");
    // !!
    /* Note: Here we are adding white space character for token as we dont want to have our token with space after it
Stringtokenizer object to token the string words with param delimeters
*/
    String syntax_for_c_language = ",.<>/?;:'\"`~[]{}\\|!@#$%^&*()-+_= ";

    StringTokenizer tokenizer = new StringTokenizer(
      words,
      syntax_for_c_language
    );

    System.out.println("No of tokens in the file : " + tokenizer.countTokens());

    while (tokenizer.hasMoreTokens()) {
      String nextTokString = tokenizer.nextToken(); // taking next token
      if (Hash_map.containsKey(nextTokString)) {
        Hash_map.computeIfPresent(nextTokString, (k, v) -> v + 1);
      } else {
        Hash_map.put(nextTokString, 1); // populating hashmap with keys set to 1 default      }
      }
    }
    System.out.println("\n" + Hash_map);
    System.out.println("_______________________________________________");
    fis.close();
    return Hash_map;
  }
}
