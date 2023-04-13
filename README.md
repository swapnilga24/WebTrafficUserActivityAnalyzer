# WebTrafficUserActivityAnalyzer  | Session Sequence Counter

This is a Java application that generates a count of sequences of a given length in a HashMap of sessions and their corresponding page arrays. The application reads a log file in CSV format, and produces a list of the top N most common sequences of the given length.

Prerequisites
Java Development Kit (JDK) version 8 or later.
A text editor or an integrated development environment (IDE) to modify the code.
Usage
Clone or download the source code from this repository.

Open a terminal or command prompt and navigate to the directory that contains the source code files.

Compile the source code by running the command:

Copy code
javac DomoAssignment.java
Run the application by executing the command:

Copy code
java DomoAssignment
The application will prompt you to enter the sequence length and the value of N (top N sequences) in the console. Enter the values and press Enter.

The application will process the log file inputLogFile.csv in the same directory as the source code, and produce a list of the top N most common sequences of the given length.

The formatted output sequence will be displayed on the console.

Modifying the Input Log File
To use a different input log file, replace the contents of inputLogFile.csv with the contents of your file. Make sure the format of the file matches the format of the sample log file, and do not change the name of the file.
