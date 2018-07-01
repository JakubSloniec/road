package com.sloniec.road.shared.commons;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

public class CSVCommons {
    private static final String LINE_SEPARATOR = "\n";

    public static boolean toCSV(String outputFile, List<String> fileHeader, List<List<String>> results) {

        if (areIncorrectParameters(outputFile, fileHeader, results)) {
            return false;
        }

        FileWriter fileWriter = null;
        CSVPrinter csvPrinter = null;
        CSVFormat csvFormat = CSVFormat.DEFAULT.withRecordSeparator(LINE_SEPARATOR);

        try {
            fileWriter = new FileWriter(outputFile);
            csvPrinter = new CSVPrinter(fileWriter, csvFormat);
            csvPrinter.printRecord(fileHeader);

            for (List<String> result : results) {
                csvPrinter.printRecord(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvPrinter.close();
            } catch (IOException e) {
                System.out.println("Wysapil blad przy zapisywaniu wynikow.");
                System.exit(0);
            }
        }
        return true;
    }

    private static boolean areIncorrectParameters(String outputFile, List<String> fileHeader, List<List<String>> results) {
        if (null == fileHeader || fileHeader.size() == 0) {
            return true;
        }
        if (null == results || results.size() == 0) {
            return true;
        }
        if (outputFile == null || outputFile.equals("")) {
            return true;
        }
        return false;
    }
}
