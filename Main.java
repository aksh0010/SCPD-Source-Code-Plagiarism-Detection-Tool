/**
 *  @author: AKSH SANJAYBHAI PATEL
 *  @Institution : University of Windsor
 *  @E-mail : akshpatelofficial@gmail.com
 */

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

  /*!Fingerprint Hashmaps
   * Here two hashmaps are created to store the unique token extracted from files
   */
  public static HashMap<String, Integer> fingerprintHashMap1 = new HashMap<>();
  public static HashMap<String, Integer> fingerprintHashMap2 = new HashMap<>();

  /*!Operator Hashmaps
   * Here two hashmaps are created to store the extracted operands from each files
   * Note: It is defined which operatands to extract in operator_extraction function
   */
  public static HashMap<Character, Integer> operatorHashMap1 = new HashMap<>();
  public static HashMap<Character, Integer> operatorHashMap2 = new HashMap<>();

  public static int common_tokens = 0;
  public static int no_of_tokens = 0; // Declaring this variable bcoz two maps could have different no of tokens so we need to choose a highest no of tokens to match and give percentage

  // !! __________________________________________________________________________
  // !!__________________________________ Main _____________________________________
  // !! ___________________________________________________________________________
  public static void main(String args[]) {
    System.out.println(
      "\n________________________________________________________________________________"
    );
    try {
      File file1 = new File(
        "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD\\Temp.c"
      ); //   creating file for the first file we need to create signature.

      File file2 = new File(
        "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD\\Temp2.c"
      ); //   creating file for the Second file we need to create signature.

      fingerprintHashMap1 = Create_token(file1);
      fingerprintHashMap2 = Create_token(file2);
      fingerprint_comparison(fingerprintHashMap1, fingerprintHashMap2);

      operatorHashMap1 = operator_extraction(file1);
      operatorHashMap2 = operator_extraction(file2);
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.println(
      "\n________________________________________________________________________________"
    );
  }

  // !! __________________________________________________________________________
  // !!_______________________________ Create Token ______________________________
  // !! ___________________________________________________________________________

  /*
   * Method Create Token takes in a file and creteas a token
   * removing all whitespaces and stores them
   * in hashmap and returns it
   *
   */
  public static HashMap<String, Integer> Create_token(File file)
    throws IOException {
    final HashMap<String, Integer> Local_Hash_map = new HashMap<>();

    FileInputStream fis = new FileInputStream(file); //opens a connection to an actual file
    byte bytearray[] = new byte[fis.available()]; //  byte[] to read data file in to byte array
    fis.read(bytearray);

    String words = new String(bytearray); //  get the string value from byte []

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
      if (Local_Hash_map.containsKey(nextTokString)) {
        Local_Hash_map.computeIfPresent(nextTokString, (k, v) -> v + 1);
      } else {
        Local_Hash_map.put(nextTokString, 1); // populating hashmap with keys set to 1 default      }
      }
    }
    System.out.println("\n" + Local_Hash_map);
    System.out.println("_______________________________________________");
    fis.close();
    return Local_Hash_map;
  }

  // !! __________________________________________________________________________
  // !!___________________FingerPrint Coomparison_________________________________
  // !! ___________________________________________________________________________
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

  // !! __________________________________________________________________________
  // !!_____________________ Operator Extraction _________________________________
  // !! ___________________________________________________________________________

  public static HashMap<Character, Integer> operator_extraction(File file)
    throws IOException {
    /*!SECTION
     * Popuplating it with necessary operands and
     * by default 0 as their occurence
     */
    final HashMap<Character, Integer> Local_operator_HashMap = new HashMap<>();

    // !! Creating a connection to original file1
    FileInputStream fis = new FileInputStream(file); //opens a connection to an actual file
    byte bytearray[] = new byte[fis.available()]; //  byte[] to read data file in to byte array
    fis.read(bytearray);
    String words_file1 = new String(bytearray);
    /*!SECTION
     * Extract count for operands from each file
     * for their comparison
     * and giving plagarism score
     *
     */
    char[] operands = { '=', '-', '+', '*', '/', '%', '&', ',', '<', '>' }; // list of operands we need for extraction
    /*NOTE - We can simply add new operands in char[] operands and
     * it will automatically add it hashmap with by default 0 values
     */
    for (int i = 0; i < words_file1.length(); i++) {
      for (char j : operands) {
        if (words_file1.charAt(i) == j) {
          Local_operator_HashMap.computeIfAbsent(j, (k -> 0));
          Local_operator_HashMap.computeIfPresent(j, (k, v) -> v + 1);
        }
      }
    }

    System.out.println(
      "\n\n Operand Hashmap has size = " +
      Local_operator_HashMap.size() +
      " | " +
      Local_operator_HashMap
    );
    fis.close();
    return Local_operator_HashMap;
  }
}
