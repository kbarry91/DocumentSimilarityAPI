# Document Similarity API
This aim of this program was to create a Java API that can rapidly compare two large text files by
computing their Jaccard Index.

## To run the project

In command prompt navigate to the directory with the oop.jar file
```
 java –cp ./oop.jar ie.gmit.sw.Runner
``` 

 Once the program is running the user will be prompted with a menu. Input '1' to start document similarity or '2' to exit.
 Then the user will be prompted to enter the first file name, followed by the 2nd file name, followed by the amount of shingles you would like to use. A shingle determines how accurate you would like it to be, the lower the shingle size the more comparisons will be tested. The API will rapidly compute the jaccard index and display the results in the console.
 
## Design Decisions

1.  File structure: all classes are packaged within packages related to their task eg.(JaccardIndex.java ,MiniHasher.java,SimilarityIndex.java are in the ie.gmit.sw.similarity package)
2.  Services.java: this is a class created to store methods that would be use full throughout the program such as  getInputString(String input)
3. Abstract Class: I decided to make a abstract SimilarityIndex.java class ,this allows the program to be easily updated to allow for different methods of comparison. The JaccardIndex.java class extends SimilarityIndex.java
4. File Input: When entering a file name the program automatically checks if it has the .txt extension, if not it is added.
5. Result to user: As well as displaying the jaccard index the program also provides a summary detailing how many shingles were created for each file and the number of comparisons.
6. Security: To protect the variables in the program from being altered incorrectly I made as many methods and variables private protected or final.

## Updates I intend to make

*   Create a parser interface to allow different forms of input such as a URL or string. 
*   Re-model the program using JOptionPane dialog boxes .
*   Allow the similarity to be checked on more than 2 documents at a time.

## Other
*  I generated javdocs through command prompt by entering the following command in the src directory of the project 
```
javadoc -d [path to javadoc destination directory] ie.gmit.sw
```
*  I also created a UML Class Diagram to show the relationships between classes.
 