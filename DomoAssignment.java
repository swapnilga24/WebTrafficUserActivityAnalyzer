import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

public class DomoAssignment {
    /**
     * Reads a CSV file and creates a hash map of session IDs and the pages visited in each session.
     * @param inputFileName the name of the CSV file to be read
     * @param sessionPagesMap the hash map to store the session IDs and pages visited
     */
    public static void readCSVFileAndCreateHashMap(String inputFileName,
            HashMap<String, ArrayList<String>> sessionPagesMap) {
        BufferedReader csvReader = null;
        try {
            csvReader = new BufferedReader(new FileReader(inputFileName));
            // Read the header line
            String line = csvReader.readLine();
            // Read each subsequent line in the file
            while ((line = csvReader.readLine()) != null) {
                // Split the line into an array of data
                String[] data = line.split(",");
                // Check if the session ID is already in the hash map
                if (sessionPagesMap.containsKey(data[1])) {
                    // If the session ID exists, add the page to the ArrayList of pages visited
                    ArrayList<String> pageList = sessionPagesMap.get(data[1]);
                    pageList.add(data[2]);
                    sessionPagesMap.put(data[1], pageList);
                } else {
                    // If the session ID does not exist, create a new ArrayList with the current page and add it to the hash map
                    ArrayList<String> pageList = new ArrayList<>(Arrays.asList(data[2]));
                    sessionPagesMap.put(data[1], pageList);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (csvReader != null) {
                try {
                    csvReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
    This function generates a count of sequences of a given sequenceLength in a HashMap of sessions and their corresponding page arrays.
    It iterates through the page arrays of each session in the sessionPagesMap and creates sublists of length sequenceLength.
    It then joins the sublists into a comma-separated string, which is used as a key in the sequenceCountMap HashMap.
    The value corresponding to each key is the count of how many times that particular sequence of pages appears in the sessions.
    @param sessionPagesMap A HashMap of sessions and their corresponding page arrays.
    @param sequenceLength The length of sequences to be counted.
    @param sequenceCountMap A HashMap that will hold the counts of the sequences.
    */
    public static void generateSequenceCountMap(HashMap<String, ArrayList<String>> sessionPagesMap,
            int sequenceLength, HashMap<String, Integer> sequenceCountMap) {
        // Iterate through the values of the sessionPagesMap HashMap        
        for (ArrayList<String> pages : sessionPagesMap.values()) {
            // If the length of the pages ArrayList is less than the sequenceLength, skip to the next iteration
            if (pages.size() < sequenceLength) {
                continue;
            }
            // Iterate through all possible sub-sequences of length sequenceLength in the pages ArrayList
            for (int i = 0; i <= pages.size() - sequenceLength; i++) {
                // Create a new ArrayList of length sequenceLength from the current sub-sequence
                ArrayList<String> subPages = new ArrayList<>(pages.subList(i, i + sequenceLength));
                // Create a String representation of the sub-sequence using commas as separators
                String sequenceKey = String.join(",", subPages);
                // Update the sequenceCountMap HashMap with the current sub-sequence count
                sequenceCountMap.put(sequenceKey, sequenceCountMap.getOrDefault(sequenceKey, 0) + 1);
            }
        }
    }

    /**
    * This function generates a list of top N sequences with their corresponding frequency count by creating a priority queue.
    * The priority queue is created based on the frequency count of each sequence. The higher the frequency count, the higher the priority of the sequence.
    * The top N sequences are then retrieved from the priority queue and added to the resultStringArray in the format "<sequence>:<frequency>".
    * @param outputSequenceArray: The list to which the top N sequences with counts will be added
    * @param topNSequences: The number of top N sequences needed
    * @param sequenceCountMap: The HashMap containing sequences and their counts
    */
    public static void generateTopNSequences(ArrayList<String> outputSequenceArray, int topNSequences,
            HashMap<String, Integer> sequenceCountMap) {
        // Create a priority queue to store the entries from the sequenceCountMap in descending order of their counts.
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(
                (a, b) -> Integer.compare(b.getValue(), a.getValue()));

        // Add all the entries from the sequenceCountMap to the priority queue.
        pq.addAll(sequenceCountMap.entrySet());

        // Retrieve the top N sequences from the priority queue and store them in outputSequenceArray.
        for (int i = 0; i < topNSequences && !pq.isEmpty(); i++) {
            Map.Entry<String, Integer> entry = pq.poll();
            String temp = entry.getKey() + ":" + entry.getValue();
            outputSequenceArray.add(temp);
        }
    }

    /**
    * Main function of the program. Reads input from user and executes the sequence generation and counting operations.
    */
    public static void main(String args[]) {
        // Create a new Scanner object to read input from the command line.
        Scanner scan = new Scanner(System.in);

        // Set the input filename to a default value
        String inputFileName = "inputLogFile.csv";

        System.out.print("Please Enter Sequence Length :");
        int sequenceLength = scan.nextInt();

        System.out.print("Please Enter value of N (Top N Sequence):");
        int topNSequences = scan.nextInt();
        scan.close();

        // Create a new HashMap to hold session/page mappings
        HashMap<String, ArrayList<String>> sessionPagesMap = new HashMap<>();

        // Read the CSV file and create the session/page mapping
        readCSVFileAndCreateHashMap(inputFileName, sessionPagesMap);

        // Create a new HashMap to hold the counts for each sequence
        HashMap<String, Integer> sequenceCountMap = new HashMap<>();

        // Generate the count for each sequence of the given length
        generateSequenceCountMap(sessionPagesMap, sequenceLength, sequenceCountMap);

        // Create a new ArrayList to hold the top N sequences
        ArrayList<String> outputSequenceArray = new ArrayList<>();

        // Generate the top N sequences and add them to the outputSequenceArray
        generateTopNSequences(outputSequenceArray, topNSequences, sequenceCountMap);

        System.out.println("Formatted Output squence for input CSV :");
        for (String record : outputSequenceArray) {
            System.out.println(record);
        }
        if(outputSequenceArray.size()==0){
            System.out.println("No sequnce possible for input csv.");    
        }
    }
}