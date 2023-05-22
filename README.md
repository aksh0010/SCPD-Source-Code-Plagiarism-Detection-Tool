# SCPD- Source Code Plagiarism Detection 
This program is made to detect similarities amongst two 'c' programs/Files.

## Research paper used while developing this project includes but are not limited to 

      1) Academic_Source_Code_Plagiarism_Detection_by_Measuring_Program_Behavioral_Similarity by HAYDEN CHEERS , YUQING LIN , AND SHAMUS P. SMITH
      2) Material Survey on Source Code Plagiarism Detection in Programming Courses
      3) An Approach to Source-Code Plagiarism Detection and Investigation Using Latent Semantic Analysis Georgina Cosma and Mike Joy 
      4)FINDING SIMILAR FILES IN A LARGE FILE SYSTEM, Udi Manber, Department of Computer Science, University of Arizona
      5)Alignment-free sequence comparison: benefits, applications, and tools, Andrzej Zielezinski1, Susana Vinga2, Jonas Almeida3 and Wojciech M. Karlowski1*
      
### NOTE: For importing org.apache.commons.text.similarity.* 

      1) Get Binary zip package from : https://commons.apache.org/proper/commons-text/download_text.cgi
      2) Below are two youtube videos either of them should work for you.
            --> https://www.youtube.com/watch?v=3Qm54znQX2E
            --> https://www.youtube.com/watch?v=iHbiY1i4ivc

## Requirements 

The most important requirement for this project is to know about the ways plagiarism can take place. For example, a 
student can modify the code, but the requirement is to learn about different ways a student can modify code to make 
it their work. 

### Such level could be as below 

   1) Level 1 : Changes to comments and indentation.
   2) Level 2 : Changes to identifiers.
   3) Level 3 : Changes in declarations (e.g. declaring extra constants,changing the order of functions and variables).
   4) Level 4 : Modifying functions (e.g. modifying signature, merging and creating new functions).
   5) Level 5 : Changing program statements to semantic equivalents(e.g. for to while, if to switch).
   6) Level 6 : Changes in decision logic and modifying expressions.
 


This Project is motivated from below approaches(but not the only ones):

   1) Structural Approach
   2) Semantic Approach
   3) Behavioral Approach 
   
 
