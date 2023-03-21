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

  static final int execution_average_constant = 1; // constant to use for eg: 10 times of iteration for average execution speed of single file
  static final int percentage_above_isPlagried = 60;

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

  // !!  *****************************************************************************
  // !!  _________________________________ Main ______________________________________
  // !!  *****************************************************************************

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

      if (
        compileCfile(file1_name, file1_path_for_complier) == -1 ||
        compileCfile(file2_name, file2_path_for_complier) == -1
      ) {
        System.out.println(
          "As one of the file has syntax error: Checking for plagarism could result into miscalculated results"
        );
      }
      double randomWalkAlgorithm_score = RandomWalkAlgorithm(s1, s2);
      double Levenshtein_score = Levenshtein(s1, s2);
      double jaccard_score = jaccard_algorithm(s1, s2);
      double fingerprint_score = fingerprint_comparison(
        Create_token(file1),
        Create_token(file2)
      );

      double[] arr = {
        randomWalkAlgorithm_score,
        Levenshtein_score,
        fingerprint_score,
        jaccard_score,
      };
      double final_score = findScore(arr);
      System.out.println(
        "\n\n\t\t_____________________________________________________________________"
      );
      System.out.println(
        "\t\tNOTE: This program will report the plagarism only if it is above " +
        percentage_above_isPlagried +
        "%"
      );
      System.out.println(
        "\t\t_____________________________________________________________________\n\n"
      );
      if (percentage_above_isPlagried <= final_score) {
        System.out.println(
          "Files are plagarised\nScore :" + (int) final_score + "%"
        );
        System.err.println(
          "See below results for more advance knowledge of each algorithm output\n"
        );
        System.out.println(
          "Jaccard score : " +
          (int) jaccard_score +
          "\nRadomWalk Algorithm : " +
          (int) randomWalkAlgorithm_score +
          "\nlevenshtein Distance Score : " +
          (int) Levenshtein_score +
          "\nFingerprint Score : " +
          (int) fingerprint_score
        );
      } else {
        System.out.println("Files are less likely to be plagarised:");
      }
      System.out.println(
        "\n________________________________________________________________________________________________________________________________"
      );
    } catch (Exception e) {
      System.err.println("Error in main ");
      e.printStackTrace();
    }
  }

  /**
   * This method is created to find the best possible score.
   * Each algorithm works differently when input strings are different and thus
   * this method finds the average of the scores and then find the distance between the min and max value
   * from the array. The closest value to mean is returned.
   * @param arr array of scores from different algorithm
   * @return returns most accurate score from the array.
   */

  public static double findScore(double arr[]) {
    Arrays.sort(arr);
    double middleValues = 0;
    double middleValues_index = (double) (arr.length / 2);
    // System.out.println("Middle value index = " + middleValues_index);
    if (middleValues_index % 2 == 0) {
      middleValues =
        (arr[(int) middleValues_index - 1] + arr[(int) middleValues_index]) /
        2.0;
    } else {
      middleValues = arr[(int) middleValues_index - 1];
    }

    double diff_max_value = Math.abs(arr[arr.length - 1] - middleValues);
    double diff_min_value = Math.abs(middleValues - arr[0]);
    /*
     * If the differences are too high(15%) then send the average of all
     * algorithms else send the most acurate one
     */
    if (
      Math.abs(arr[arr.length - 1] - arr[0]) >= 15 &&
      Math.abs(arr[arr.length - 1] - arr[0]) < 20
    ) {
      middleValues = 0;
      for (int i = 0; i < arr.length; i++) {
        middleValues = middleValues + arr[i];
      }
      middleValues = middleValues / arr.length;
      return middleValues;
    }
    /*
     * If difference is greater than 20 % then return the lowest percentage
     *
     */
    if (arr[arr.length - 1] - arr[0] >= 20) {
      return arr[0];
    }
    /*
     * if the difference is less than 5% return middle value
     */
    if (Math.abs(arr[arr.length - 1] - arr[0]) <= 5) {
      return middleValues;
    }
    /*
    if none of the above then return that mean range is 5-15% then do belo
     */
    if (diff_max_value < diff_min_value) {
      return arr[arr.length - 1]; // returning the highest score as it is closest to mean
    } else {
      return arr[0]; // returning the lowest score as it is closest to mean
    }
  }

  /**
   * This function takes in two strings and creates a sets of each unique char from strings
   * Function uses union method for total size of all unique chars from both sets
   * also uses retainall method to get common chars
   * @param str1 String input1
   * @param str2 String input2
   * @return double score from this algorithm
   */
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
    Set<Character> intersection = new HashSet<>(set1);
    intersection.retainAll(set2);
    Set<Character> union = new HashSet<>(set1);
    union.addAll(set2);
    double jaccard = (double) intersection.size() * 100 / union.size();

    return jaccard;
  }

  /**
   * This Function takes in two input string and creates a single set of substrings of a fixed length
   * Then it represents the set as the Map of 2 vector using Map as key,value(CharSequence, int)
   * NOTE: CharSequence is used with fixed length and thus it could effect the results
   * After creating Vectors, It uses cosin similarity approach by calculating DOT product of the vectors.
   * @param s1 String input1
   * @param s2 String input1
   * @return return the score in double
   */
  public static double RandomWalkAlgorithm(String s1, String s2) {
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
    double similarity =
      (cosineSimilarity.cosineSimilarity(vectors[0], vectors[1])) * 100;
    return similarity;
  }

  /**
   * Token Edit Distance is an algorithm which works by populating matirx of length m cross n with S1 on first row while S2 on first col
   * Rest of the row and cols are filled by measuring the distance it has to travel in order to convert from one token to other
   * The bottom right corner cell in the created matrix is the total work needed to modify one string to other.
   * and as the bottom right cell is work needed, we subtract it on scale of 100 to get how much work is similar as we dont have to modify it
   * @param s1 String input1
   * @param s2 String input2
   * @return Returns the score
   */
  public static double Levenshtein(String s1, String s2) {
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

  /**
   * This function is required to remove the comments from the text as in C files, users could change the comments without changing the code
   * which could lead to low score. Thus this function removes both kinds of comments.
   * NOTE: we cannot use regex to split it as it could cut the \n chars as well
   * @param input String to remove comments from
   * @return returns the new string without comments
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

  /**
   * Method Create Token takes in a file and creteas a token
   * removing all whitespaces and following c cyntax and stores them
   * in hashmap and returns it
   * @param file takes input file
   * @return returns hashmap of uniques token and their freq.
   * @throws IOException
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

  /**
   * This function takes in 2 hashmap and evaluates the similiraity among them
   * comparaing their values and return the score
   * @param map1
   * @param map2
   * @return the score of plagarism
   */

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

  /**
   * This function takes in File as param and returns Hashmap of unique operators and their frequency
   * @param file
   * @return Hashmap <Char,Int>
   * @throws IOException
   */

  public static HashMap<Character, Integer> operator_extraction(File file)
    throws IOException {
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
    return Local_operator_HashMap;
  }

  /**
   * This function is simply extracting String From file function
   * I noticed code was duplicated thorught out so decided to make a function
   * for resuability
   * @param file Takes in File from which we need to extract the String
   * @return Final string after removing neccessary words an white space
   * @throws IOException
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
      System.err.println("Error in string_from_file : ");
      e.printStackTrace();
    }
    return file_words;
  }

  /**
   * This function takes in two Hashmap of operator and compares then
   * @deprecated need more work
   * @param map1
   * @param map2
   */
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

  /**
   *This function takes in filename and path of the file and complies the program and returns compile time else -1
   * @param filename
   * @param directory_path
   * @return time for complie the file using cmd
   * @throws InterruptedException
   */
  public static long compileCfile(String filename, File directory_path)
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
    } catch (Exception e) {
      System.err.println("Unknown erro in compliecFile function");
      e.printStackTrace();
      return -1;
    }
  }

  // ?? __________________________________________________________________________
  // ??______________________ Process for compilation Function ________________________
  // ?? ___________________________________________________________________________
  /**
   * Initial purpose was to use the complie time of each program file and compute them but
   * it turned out to be ineffective as compile time could be different each time we run same program
   * @deprecated Not effecitive to use at the moment
   * @param filename1
   * @param directory_path1
   * @param filename2
   * @param directory_path2
   * @throws InterruptedException
   */
  public static void Compare_compileCfile_output_only(
    String filename1,
    File directory_path1,
    String filename2,
    File directory_path2
  ) throws InterruptedException {
    // System.out.println(
    //   "_______________________________________________Compare_compileCfile_output_only _______________________________________________"
    // );
    long average_time_file1 = 0;
    long average_time_file2 = 0;
    boolean compilation_success = true;
    for (int i = 0; i < execution_average_constant; i++) {
      average_time_file1 += compileCfile(filename1, directory_path1);
      average_time_file2 += compileCfile(filename2, directory_path2);
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
}
