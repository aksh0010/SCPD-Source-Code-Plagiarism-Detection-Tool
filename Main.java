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

  // static final int REGEX_TOKEN_SIZE = 5; //this token size is needed for regex token comparison and cant be changes
  static final int execution_average_constant = 1; // constant to use for 10 times of iteration for average execution speed of single file
  static final int percentage_above_isPlagried = 50;

  static final String file1_name = "Temp.c";
  static final File file1 = new File(
    "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD\\" +
    file1_name
  ); //   creating file for the first file we need to create signature.
  static final File file1_path = new File(
    "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD"
  ); //   creating file for the first file we need to create signature.

  static final String file2_name = "Temp2.c";
  static final File file2 = new File(
    "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD\\" +
    file2_name
  ); //   creating file for the Second file we need to create signature.

  static final File file2_path = new File(
    "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A\\JAVA\\SCPD"
  );

  // public static int common_tokens = 0;
  // public static int no_of_tokens = 0; // Declaring this variable bcoz two maps could have different no of tokens so we need to choose a highest no of tokens to match and give percentage

  // public static int operand_file1 = 0;
  // public static int operand_file2 = 0;
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

  // !!_______________________________________________________________________________________________
  public static int token_edit_distance_algorithm(String s1, String s2) {
    String[] tokens1 = s1.split("\\s+");
    String[] tokens2 = s2.split("\\s+");

    // calculate the token edit distance
    int[][] dp = new int[tokens1.length + 1][tokens2.length + 1];
    for (int i = 0; i <= tokens1.length; i++) {
      for (int j = 0; j <= tokens2.length; j++) {
        if (i == 0) {
          dp[i][j] = j;
        } else if (j == 0) {
          dp[i][j] = i;
        } else {
          int cost = tokens1[i - 1].equals(tokens2[j - 1]) ? 0 : 1;
          dp[i][j] =
            Math.min(
              dp[i - 1][j - 1] + cost,
              Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1)
            );
        }
      }
    }
    System.out.println(
      "_______________________________________________TOKEN EDIT DISTANCE ALGORITHM OUTPUT _______________________________________________"
    );
    double common_tokens = dp[tokens1.length][tokens2.length];
    double total_tokens = tokens1.length + tokens2.length;
    double percentage = 100 - ((common_tokens * 100) / total_tokens);
    System.out.println(
      "Similarity score using edit token algo : " + percentage
    );
    if (percentage == 0) {
      System.out.println("The two strings are not similar.");
    } else if (percentage >= percentage_above_isPlagried) {
      System.out.println("The two strings are likely to be plagiarized.");
    } else if (percentage == 100) {
      System.out.println("The two strings are identical");
    }

    /* if (dp[tokens1.length][tokens2.length] == 0) {
      System.out.println("The two strings are identical.");
    } else if (dp[tokens1.length][tokens2.length] <= 3) {
      System.out.println("The two strings are likely to be plagiarized.");
    } else {
      System.out.println("The two strings are not similar.");
    } */
    return dp[tokens1.length][tokens2.length];
  }

  // !!   *************************************************************************
  // !! __________________________________ Main _____________________________________
  // !!  **************************************************************************
  public static void main(String args[]) {
    System.out.println(
      "\n________________________________________________________________________________________________________________________________"
    );
    try {
      // delcaring below as final so we cant edit them throgh out code
      /*   File file1 = new File(
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
      ); */

      /**NOTE - C complier function Below.
       */
      /*  long average_time_file1 = 0;
      long average_time_file2 = 0;
      boolean compilation_success = true;
      for (int i = 0; i < execution_average_constant; i++) {
        average_time_file1 += ExecSpeedOf_Cprog("Temp.c", file1_path);
        average_time_file2 += ExecSpeedOf_Cprog("Temp2.c", file2_path);
        if (average_time_file1 == -1 || average_time_file2 == -1) {
          compilation_success = false;
          System.out.println(
            "One of the files has compile time error to proceed for execution speed comparison.\n"
          );
          break;
        }
      }
      if (compilation_success) {
        average_time_file1 = average_time_file1 / execution_average_constant;
        average_time_file2 = average_time_file2 / execution_average_constant;
        System.out.println(
          "Average Execution speed of file 1 in milli seconds " +
          average_time_file1
        );
        System.out.println(
          "Average Execution speed of file 2 in milli seconds " +
          average_time_file2
        );
        System.out.println(
          "Difference between execution speeds is " +
          Math.abs(average_time_file2 - average_time_file1)
        );
      } */

      /* Note: Here we are adding white space character for token as we dont want to have our token with space after it
      Stringtokenizer object to token the string words with param delimeters
      */

      /*ANCHOR - Structural Apporach for detecting Plagrism
       * Token Edit distance Algorithm
       * Fingerprint Comparison Algorithm
       * Operand Comparison
       */
      Compare_ExecSpeedOf_Cprog_output_only(
        file1_name,
        file1_path,
        file2_name,
        file2_path
      );
      String s1 = string_from_file(file1);
      String s2 = string_from_file(file2);
      token_edit_distance_algorithm(s1, s2);
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
      "\n________________________________________________________________________________________________________________________________"
    );
  }

  // !!  **************************************************************************
  // !!  **************************************************************************

  // !! ____________________________________________________________________________________________________________________________________________________
  // !!_______________________________ Operator occurence ______________________________
  // !! ___________________________________________________________________________

  // !! ____________________________________________________________________________________________________________________________________________________
  // !!_______________________________ Remove comments from files ______________________________
  // !! ___________________________________________________________________________
  /*!SECTION
   * Removing both types of comments from the files before tokening
   */
  public static String comment_removal_from_c_file_string(String input) {
    for (int i = 0; i < input.length() - 2; i++) {
      if (input.charAt(i) == '/' && input.charAt(i + 1) == '/') {
        int index_of_newlineChar = input.indexOf("\n", i);
        String string_to_delete = input.substring(i, index_of_newlineChar);

        input = input.replaceAll(string_to_delete, "");
      } else if (input.charAt(i) == '/' && input.charAt(i + 1) == '*') {
        int index_of_newlineChar = input.indexOf("*/", i);
        String string_to_delete = input.substring(i, index_of_newlineChar);

        input = input.replaceAll(string_to_delete, "");
      }
    }

    return input;
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

    // System.out.println(
    //   "No of tokens in the file -> " + tokenizer.countTokens()
    // );

    while (tokenizer.hasMoreTokens()) {
      String nextTokString = tokenizer.nextToken(); // taking next token
      if (Local_Hash_map.containsKey(nextTokString)) {
        Local_Hash_map.computeIfPresent(nextTokString, (k, v) -> v + 1);
      } else {
        Local_Hash_map.put(nextTokString, 1); // populating hashmap with keys set to 1 default      }
      }
    }
    // System.out.println("\n" + Local_Hash_map);

    return Local_Hash_map;
  }

  // !! __________________________________________________________________________
  // !!___________________FingerPrint Comparison_________________________________
  // !! ___________________________________________________________________________
  public static void fingerprint_comparison(
    HashMap<String, Integer> map1,
    HashMap<String, Integer> map2
  ) {
    int common_tokens = 0;
    int no_of_tokens = 0;
    // !! Checking set comprison for subsets
    System.out.println(
      "_______________________________________________FINGERPRINT COMPARISON ALGORITHM OUTPUT _______________________________________________"
    );
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
      no_of_tokens = Math.max(map1.keySet().size(), map2.keySet().size());

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

    // System.out.println("\n\n Operand Hashmap -> " + Local_operator_HashMap);
    return Local_operator_HashMap;
  }

  public static void Compare_ExecSpeedOf_Cprog_output_only(
    String filename1,
    File directory_path1,
    String filename2,
    File directory_path2
  ) throws InterruptedException {
    long average_time_file1 = 0;
    long average_time_file2 = 0;
    boolean compilation_success = true;
    for (int i = 0; i < execution_average_constant; i++) {
      average_time_file1 += ExecSpeedOf_Cprog(filename1, directory_path1);
      average_time_file2 += ExecSpeedOf_Cprog(filename2, directory_path2);
      if (average_time_file1 == -1 || average_time_file2 == -1) {
        compilation_success = false;
        System.out.println(
          "One of the files has compile time error to proceed for execution speed comparison.\n"
        );
        break;
      }
    }
    if (compilation_success) {
      average_time_file1 = average_time_file1 / execution_average_constant;
      average_time_file2 = average_time_file2 / execution_average_constant;
      System.out.println(
        "Average Execution speed of file 1 in milli seconds " +
        average_time_file1
      );
      System.out.println(
        "Average Execution speed of file 2 in milli seconds " +
        average_time_file2
      );
      System.out.println(
        "Difference between execution speeds is " +
        Math.abs(average_time_file2 - average_time_file1)
      );
    }
  }

  // !! __________________________________________________________________________
  // !!______________________ Process for compilation Function ________________________
  // !! ___________________________________________________________________________

  public static long ExecSpeedOf_Cprog(String filename, File directory_path)
    throws InterruptedException {
    long startTime;
    long elapsedTime;
    try {
      startTime = System.nanoTime();
      Process p = Runtime
        .getRuntime()
        .exec("cmd /C gcc -Wall " + filename, null, directory_path);
      int exit_code = p.waitFor();

      if (exit_code == 0) {
        elapsedTime = System.nanoTime() - startTime;
        System.out.println("Compiling " + filename + " was a success ^_^");
        return (elapsedTime / 1000000);
      } else {
        System.out.println("Error Compiling " + filename + " file .....");
        return -1;
      }
    } catch (IOException e) {
      e.printStackTrace();
      return -1;
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
      file_words = comment_removal_from_c_file_string(file_words);
      //how to compare file_words to another string inorder to compare for plagarism ?  !SECTION

      //Source: https://stackoverflow.com/questions/43092414

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
    int operand_file1 = 0;
    int operand_file2 = 0;
    System.out.println(
      "_______________________________________________OPERAND COMPARISON ALGORITHM OUTPUT _______________________________________________"
    );
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
}
