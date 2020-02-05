## Solution for converting a text file to a formatted pdf file

To create this I have followed somewhat a TDD approach, writing basic unit tests to check reading and writing to a file
and checking formatting functionalities using iText 7. For testing this I used a small sample input file and this generated
the expected output. I then moved on to implementing my main main program.

### Logic

From the input file what can be derived is the ordering of commands and text, i.e. text appears after a format command and 
so I use an array-list to capture the text and the formatting commands responsible for that text. I then call another
method which handles the formatting of the text.

### Requirements & Installation 

I have used Java and Maven build tool to create the solution. I have used IntelliJ IDE to build and run it.
If using IntelliJ then simply import the maven changes and set the run configurations and run it.

Requirements
```
-Java 1.8

-MVN
```

### Assumptions
- I have assumed that all commands start with a 'full-stop'. It will behave erroneously if a sentence of a paragraph full stop appears as the first char of the line
- I have pre-defined when to add the texts to the document, e.g. when a new paragraph is called I add the existing one to the document
- Using a scanner, I check if the next line belongs to the current paragraph and if so i add that as a string builder. This allows me to handle indentation easily.

### Possible improvements
- I find that Scanner is not efficient for all usage, so next time I am likely to use BufferedInputStream.
- Use of a better data structure, e.g. queue, linked_list to split the cmds and text using a regex/delimiter and then process it all together.
- A better library than iText 7 as I couldn't find good explanations for some of the questions that arose during development.



