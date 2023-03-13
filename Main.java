/**
 *  @author: AKSH SANJAYBHAI PATEL
 *  @Institution : University of Windsor
 *  @E-mail : akshpatelofficial@gmail.com
 */

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.commons.text.similarity.*;

public class Main {

  static final int execution_average_constant = 1; // constant to use for 10 times of iteration for average execution speed of single file
  static final int percentage_above_isPlagried = 50;

  static final String file1_name = "Temp.c";
  static final File file1 = new File(
    "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A and B\\JAVA\\SCPD\\" +
    file1_name
  ); //   creating file for the first file we need to create signature.
  static final File file1_path_for_complier = new File(
    "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A and B\\JAVA\\SCPD"
  ); //   creating file for the first file we need to create signature.

  static final String file2_name = "Temp2.c";
  static final File file2 = new File(
    "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A and B\\JAVA\\SCPD\\" +
    file2_name
  ); //   creating file for the Second file we need to create signature.

  static final File file2_path_for_complier = new File(
    "C:\\Users\\akshr\\Desktop\\University\\6 Fall 2022\\COMP4990-A and B\\JAVA\\SCPD"
  );

  // !!   *************************************************************************
  // !! __________________________________ Main _____________________________________
  // !!  **************************************************************************
  public static void main(String args[]) {
    System.out.println(
      "\n________________________________________________________________________________________________________________________________"
    );
    try {
      /* Note: Here we are adding white space character for token as we dont want to have our token with space after it
      Stringtokenizer object to token the string words with param delimeters
      */
      String s1 = string_from_file(file1);
      String s2 = string_from_file(file2);
      /*  Compare_ExecSpeedOf_Cprog_output_only(
        file1_name,
        file1_path_for_complier,
        file2_name,
        file2_path_for_complier
      );

       */
      /*ANCHOR - Semantic Approaches for detecting Plagrism
       * Radom Walk Algorithm
       */

      double RandomWalkAlgorithm_score = RandomWalkAlgorithm(s1, s2);
      double TokenEdit_score = token_edit_distance_algorithm(s1, s2);
      double jaccard_score = jaccard_algorithm(s1, s2);
      /*ANCHOR - Structural Approaches for detecting Plagrism
       * Token Edit distance Algorithm
       * Fingerprint Comparison Algorithm
       * Operand Comparison
       */
      double Fingerprint_score = fingerprint_comparison(
        Create_token(file1),
        Create_token(file2)
      );
      // operand_comparison(
      //   operator_extraction(file1),
      //   operator_extraction(file2)
      // );

      System.out.println("Jaccard score : " + jaccard_score);

      System.out.println(
        "Radom Walk Algorithm : " +
        RandomWalkAlgorithm_score +
        "\nToken Edit Distance Score : " +
        TokenEdit_score +
        "\nFingerprint Score : " +
        Fingerprint_score
      );
      double[] arr = {
        RandomWalkAlgorithm_score,
        TokenEdit_score,
        Fingerprint_score,
        jaccard_score,
      };
      double Similarity = findScore(arr);
      System.out.println(
        "\n\nSource Code Plagarism Score for given files is : " + Similarity
      );
    } catch (Exception e) {
      System.out.println("Error in main : ");
      e.printStackTrace();
    }
    System.out.println(
      "\n________________________________________________________________________________________________________________________________"
    );
  }

  public static double findScore(double arr[]) {
    Arrays.sort(arr);
    int middleValues_index = arr.length / 2;
    double middleValues;

    if (middleValues_index % 2 == 0) {
      middleValues =
        (arr[middleValues_index - 1] + arr[middleValues_index]) / 2.0;
    } else {
      middleValues = arr[middleValues_index - 1];
    }

    double diff_max_value = arr[arr.length - 1] - middleValues;
    double diff_min_value = arr[0] - middleValues;

    if (diff_max_value < diff_min_value) {
      return arr[arr.length - 1]; // returning the highest score as it is closest to mean
    } else {
      return arr[0]; // returning the lowest score as it is closest to mean
    }
  }

  public static double jaccard_algorithm(String str1, String str2) {
    // Create sets of characters in both strings
    Set<Character> set1 = new HashSet<>();
    Set<Character> set2 = new HashSet<>();
    for (char c : str1.toCharArray()) {
      set1.add(c);
    }
    for (char c : str2.toCharArray()) {
      set2.add(c);
    }

    // Calculate Jaccard similarity coefficient
    Set<Character> intersection = new HashSet<>(set1);
    intersection.retainAll(set2);
    Set<Character> union = new HashSet<>(set1);
    union.addAll(set2);
    double jaccard = (double) intersection.size() * 100 / union.size();

    // Return similarity score
    return jaccard;
  }

  // !! ______________________________________________________________________________________________
  public static double RandomWalkAlgorithm(String s1, String s2) {
    // System.out.println(
    //   "_______________________________________________Random Walk Algorithm OUTPUT _______________________________________________"
    // );
    // Define set of possible all_substrings
    Set<String> all_substrings = new HashSet<>();
    for (int i = 0; i < s1.length(); i++) {
      for (int j = i + 1; j <= Math.min(i + 3, s1.length()); j++) {
        all_substrings.add(s1.substring(i, j));
      }
    }
    for (int i = 0; i < s2.length(); i++) {
      for (int j = i + 1; j <= Math.min(i + 3, s2.length()); j++) {
        all_substrings.add(s2.substring(i, j));
      }
    }
    // Represent all_substrings as vectors

    Map<String, Integer> substringIndex = new HashMap<>();
    int index = 0;
    for (String substring : all_substrings) {
      substringIndex.put(substring, index++);
    }
    int total_substring = all_substrings.size();
    Map<CharSequence, Integer>[] vectors = new Map[2];
    for (int i = 0; i < 2; i++) {
      Map<CharSequence, Integer> vector = new HashMap<>();
      for (int j = 0; j < total_substring; j++) {
        vector.put((CharSequence) substringIndex.keySet().toArray()[j], 0);
      }
      vectors[i] = vector;
    }
    for (int i = 0; i < s1.length(); i++) {
      for (int j = i + 1; j <= Math.min(i + 3, s1.length()); j++) {
        String substring = s1.substring(i, j);
        if (substringIndex.containsKey(substring)) {
          vectors[0].put(substring, vectors[0].get(substring) + 1);
        }
      }
    }
    for (int i = 0; i < s2.length(); i++) {
      for (int j = i + 1; j <= Math.min(i + 3, s2.length()); j++) {
        String substring = s2.substring(i, j);
        if (substringIndex.containsKey(substring)) {
          vectors[1].put(substring, vectors[1].get(substring) + 1);
        }
      }
    }

    // Compute similarity using cosine similarity
    CosineSimilarity cosineSimilarity = new CosineSimilarity();
    // converting similarity to percentage by multiply to 100
    double similarity =
      (cosineSimilarity.cosineSimilarity(vectors[0], vectors[1])) * 100;
    // System.out.println(
    //   "RandomWalkAlgorithm Similarity between files is :" + similarity + "%"
    // );
    return similarity;
  }

  // !!_______________________________________________________________________________________________
  public static double token_edit_distance_algorithm(String s1, String s2) {
    String[] tokens1 = s1.split("\\s+");
    String[] tokens2 = s2.split("\\s+");
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

    double common_tokens = dp[tokens1.length][tokens2.length];
    double total_tokens = tokens1.length + tokens2.length;
    double similarity_score = 100 - ((common_tokens * 100) / total_tokens);

    return similarity_score;
  }

  // !! ________________________________________________________________________________________
  // !!_______________________________ Remove comments from files ______________________________
  // !! ________________________________________________________________________________________
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
  // !! __________________________________________________________________________

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

    while (tokenizer.hasMoreTokens()) {
      String nextTokString = tokenizer.nextToken(); // taking next token
      if (Local_Hash_map.containsKey(nextTokString)) {
        Local_Hash_map.computeIfPresent(nextTokString, (k, v) -> v + 1);
      } else {
        Local_Hash_map.put(nextTokString, 1); // populating hashmap with keys set to 1 default      }
      }
    }

    return Local_Hash_map;
  }

  // !! __________________________________________________________________________
  // !!___________________FingerPrint Comparison__________________________________
  // !! __________________________________________________________________________
  public static double fingerprint_comparison(
    HashMap<String, Integer> map1,
    HashMap<String, Integer> map2
  ) {
    double similarity_score = 0;
    int common_tokens = 0;
    int no_of_tokens = 0;
    // !! Checking set comprison for subsets

    Set<String> Keyset1 = new HashSet<String>();
    Set<String> Keyset2 = new HashSet<String>();

    Keyset1.addAll(map1.keySet());
    Keyset2.addAll(map2.keySet());
    no_of_tokens = Math.max(map1.keySet().size(), map2.keySet().size());

    for (String iterable_element : map1.keySet()) {
      if (map2.keySet().contains(iterable_element)) {
        common_tokens++;
      }
    }

    similarity_score = (double) (common_tokens * 100 / no_of_tokens);

    return similarity_score;
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
    // System.out.println(
    //   "_______________________________________________Compare_ExecSpeedOf_Cprog_output_only _______________________________________________"
    // );
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

      file_words = file_words.replaceAll("\\s+", " "); //  Need to get ride of junk words that is tab spaces and new line characters and replacing it with single space.
    } catch (Exception e) {
      System.out.println("Error in string_from_file : ");
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
    // System.out.println(
    //   "_______________________________________________OPERAND COMPARISON ALGORITHM OUTPUT _______________________________________________"
    // );
    for (Integer iterable_element : map1.values()) {
      operand_file1 += iterable_element;
    }
    for (Integer iterable_element : map2.values()) {
      operand_file2 += iterable_element;
    }

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

  // ?? __________________________________________________________________________
  // ??______________________ Process for compilation Function ________________________
  // ?? ___________________________________________________________________________

  public static long ExecSpeedOf_Cprog(String filename, File directory_path)
    throws InterruptedException {
    long startTime;
    long elapsedTime;
    try {
      StringBuilder cmd = new StringBuilder("gcc -Wall ");
      cmd.append(filename);

      startTime = System.nanoTime();
      ProcessBuilder builder = new ProcessBuilder();
      builder.command("cmd", "/C", cmd.toString());
      builder.directory(directory_path);
      Process p = builder.start();

      try (
        InputStream in = p.getInputStream();
        OutputStream out = p.getOutputStream()
      ) {
        int exit_code = p.waitFor();
        if (exit_code == 0) {
          elapsedTime = System.nanoTime() - startTime;
          System.out.println("Compiling " + filename + " was a success ^_^");
          return elapsedTime / 1000000;
        } else {
          System.out.println("Error Compiling " + filename + " file .....");
          return -1;
        }
      }
    } catch (IOException e) {
      System.out.println("Error in ExecSpeedOf_Cprog :");
      e.printStackTrace();
      return -1;
    }
  }
}
