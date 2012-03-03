Authors: Andrew, Jordan, and Zane
Flair Compiler README

Scanner
-------

Design:

    To complete the Scanner portion of our compiler we use two classes. These classes are the FlairScanner class and FlairToken class.

    The FlairScanner class represents the scanner. It takes in a Flair file and converts the contents to a string. Once it has this string is creates spaces around the important characters. This makes it much easier to be able to identify tokens. After creating the spaces it has a string array, which then gets scanned to see which strings are actual tokens. The adding spacing process earlier helped make easier pieces of data to deal with, but ends up making it a bit more difficult to deal with identifying tokens when they use similar characters. But this isn't too difficult to deal with and just causes it to look ahead in the string array. After a string is identified as a legit token, it is then added to the ArrayList of confirmed tokens.

    The FlairToken class is set up to create the tokens. The token is constructed to have a type and a value. Static variables are there to have the possible token type variables known.

Instructions:

Need the FlairScanner java file and FlairToken java file.
Any Flair files you want to use should be in the same folder as those classes.
Compile both java files and run the FlairScanner class with a command line argument that is the name of the file and extension.
*You need Java JDK 7 for the code to work.