package org.example;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class ResultsWriter {
    private String testName;
    private TreeMap<Integer, Long> results;

    public ResultsWriter(String testName, TreeMap<Integer, Long> results) {
        this.testName = testName;
        this.results = results;
    }

    public void write() {
        // first create file object for file placed at location
        // specified by filepath
        File file = new File("./" + testName + ".csv");

        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // create a List which contains String array
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "Messages", "Time"});
            this.results.entrySet().forEach(integerLongEntry -> {
                data.add(new String[] { String.valueOf(integerLongEntry.getKey()), String.valueOf(integerLongEntry.getValue())});
            });
            writer.writeAll(data);

            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
