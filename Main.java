/**
 *  @author: AKSH SANJAYBHAI PATEL
 *  @Institution : University of Windsor
 *  @E-mail : akshpatelofficial@gmail.com
 */

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

  static final int REGEX_TOKEN_SIZE = 5; //this token size is needed for regex token comparison and cant be changes

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

  public static int operand_file1 = 0;
  public static int operand_file2 = 0;

  // !! __________________________________________________________________________
  // !!______________________ Regex Comparison Function ________________________
  // !! ___________________________________________________________________________
  /* public static void regex_comparison(
    HashMap<Character, Integer> map1,
    HashMap<Character, Integer> map2
  ) {
    Matcher matcher;
    Pattern pattern;
    Random random = new Random();
    int rand = 0;
    String random_pattern = "";
    int count_matches = 0;
    int total_comparison = words1.length() - REGEX_TOKEN_SIZE;
    // int total_comparison = 3;
    try {
      map1.forEach((token, frequency) -> {
        System.out.println(token + " => " + frequency);
      });


      for (int i = 0; i < words1.length() - REGEX_TOKEN_SIZE - 1; i++) {
        rand = random.nextInt(words1.length() - REGEX_TOKEN_SIZE);
        random_pattern = words1.substring(rand, rand + REGEX_TOKEN_SIZE);
        // if(rand !=0) break;
        pattern = Pattern.compile("print", Pattern.CASE_INSENSITIVE);

        // for (int j = 0; j < words2.length(); j++) {
        matcher = pattern.matcher(words2);
        boolean matchFound = matcher.find();
        if (matchFound) {
          count_matches++;
          System.out.println("Match found");
        } else {
          System.out.println("Match not found");
        }
        // }
      }
      System.out.println(
        "COunt match : " + count_matches + " Total tokens : " + total_comparison
      );
      System.out.println(
        "Regex Comparison : " + count_matches * 100 / total_comparison + "%"
      );
    } catch (Exception e) {
      System.err.println(e);
    }
  }*/

  // !! __________________________________________________________________________
  // !!______________________ Process for compilation Function ________________________
  // !! ___________________________________________________________________________

  public static void CompileCprog(String filename, File directory_path) {
    System.out.println("Compiling c file .....");
    try {
      //   String exeName = filename.substring(0, filename.length() - 2);
      Process p = Runtime
        .getRuntime()
        .exec("cmd /C gcc " + filename, null, directory_path);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // !! __________________________________________________________________________
  // !!______________________ string_from_file Function ________________________
  // !! ___________________________________________________________________________

  /*!String From file function
   * I noticed code was multipled so decided to make a function
   * to make it resuable
   */
  public static String string_from_file(File file) throws IOException {
    String file_words = "";
    try (FileInputStream Local_file = new FileInputStream(file)) {
      byte bytearray[] = new byte[Local_file.available()]; //  byte[] to read data file in to byte array
      Local_file.read(bytearray);
      file_words = new String(bytearray); //  get the string value from byte []
      file_words = file_words.replaceAll("\\s+", " "); //  Need to get ride of junk words that is tab spaces and new line characters and replacing it with single space.
    } catch (Exception e) {
      System.err.println(e);
    }
    return file_words;
  }

  // !! __________________________________________________________________________
  // !!______________________ Operand Comparison Function ________________________
  // !! ___________________________________________________________________________

  public static void operand_comparison(
    HashMap<Character, Integer> map1,
    HashMap<Character, Integer> map2
  ) {
    for (Integer iterable_element : map1.values()) {
      operand_file1 += iterable_element;
    }
    for (Integer iterable_element : map2.values()) {
      operand_file2 += iterable_element;
    }

    System.out.println(" Total Operands in File 1 -> " + operand_file1);
    System.out.println(" Total Operands in File 2 -> " + operand_file2);

    if (operand_file1 < operand_file2) {
      System.out.println(
        "Operands used per file ratio for file1:file2 -> " +
        operand_file1 *
        100 /
        operand_file2 +
        '%'
      );
    } else {
      System.out.println(
        "Operands used per file ratio for file2:File1 -> " +
        operand_file2 *
        100 /
        operand_file1 +
        '%'
      );
    }
  }

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
      File file1_path = new File(
        "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD"
      ); //   creating file for the first file we need to create signature.

      File file2 = new File(
        "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD\\Temp2.c"
      ); //   creating file for the Second file we need to create signature.

      File file2_path = new File(
        "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD"
      );
      /**NOTE - C complier function Below.
       *
       *
       */

      CompileCprog("Temp.c", file1_path);
      CompileCprog("Temp2.c", file2_path);

      fingerprintHashMap1 = Create_token(file1);
      fingerprintHashMap2 = Create_token(file2);
      fingerprint_comparison(fingerprintHashMap1, fingerprintHashMap2);

      operatorHashMap1 = operator_extraction(file1);
      operatorHashMap2 = operator_extraction(file2);
      operand_comparison(operatorHashMap1, operatorHashMap2);
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
    String words = string_from_file(file);

    /* Note: Here we are adding white space character for token as we dont want to have our token with space after it
      Stringtokenizer object to token the string words with param delimeters
    */
    String syntax_for_c_language = ",.<>/?;:'\"`~[]{}\\|!@#$%^&*()-+_= ";

    StringTokenizer tokenizer = new StringTokenizer(
      words,
      syntax_for_c_language
    );

    System.out.println(
      "No of tokens in the file -> " + tokenizer.countTokens()
    );

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

    return Local_Hash_map;
  }

  // !! __________________________________________________________________________
  // !!___________________FingerPrint Comparison_________________________________
  // !! ___________________________________________________________________________
  public static void fingerprint_comparison(
    HashMap<String, Integer> map1,
    HashMap<String, Integer> map2
  ) {
    // !! Checking set comprison for subsets

    Set<String> Keyset1 = new HashSet<String>();
    Set<String> Keyset2 = new HashSet<String>();

    Keyset1.addAll(map1.keySet());
    Keyset2.addAll(map2.keySet());

    if (map1.equals(map2)) {
      System.out.println("Files are 100% identical by Text");
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

    String words_file1 = string_from_file(file);
    /*!SECTION
     * Extract count for operands from each file
     * for their comparison
     * and giving plagarism score
     *
     */
    char[] operands = { '=', '-', '+', '*', '/', '%', '&', ',', '<', '>' }; // list of operands we need for extraction
    /*We can simply add new operands in char[] operands and
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

    System.out.println("\n\n Operand Hashmap -> " + Local_operator_HashMap);
    return Local_operator_HashMap;
  }
}
