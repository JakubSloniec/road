package com.sloniec.road.module.preparator;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.nio.file.Files.newBufferedWriter;

import com.sloniec.road.framework.interf.IPreparator;
import com.sloniec.road.shared.commons.FileCommons;
import com.sloniec.road.shared.commons.TimeCommons;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WaveloFilePreparator implements IPreparator {

    private static String outputFolder;

    @Override
    public String prepare(String dataFile) {
        FileCommons.checkIfIsFile(dataFile);
        createOutputDir(dataFile);
        divideFile(dataFile);
        return outputFolder;
    }

    private void divideFile(String file) {
        log.info("Rozpoczeto dzielenie danych wavelo z pliku: [{}]", file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            BufferedWriter writer = null;
            long count = 0;
            String xmlLine = reader.readLine();
            String gpxLine = reader.readLine();

            String currentFile;
            String line = reader.readLine();
            while (line != null) {
                if (line.contains("<trk>")) {
                    count++;
                    if (writer != null) {
                        writer.write("</gpx>");
                        writer.flush();
                        writer.close();
                    }
                    currentFile = "data_" + count + ".gpx";
                    writer = newBufferedWriter(Paths.get(outputFolder, currentFile), UTF_8);
                    writer.write(xmlLine);
                    writer.newLine();
                    writer.write(gpxLine);
                    writer.newLine();
                }
                writer.write(line);
                writer.newLine();
                line = reader.readLine();
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createOutputDir(String inputFile) {
        String parent = Paths.get(inputFile).getParent().toAbsolutePath().toString();
        outputFolder = Paths.get(parent, "divided_data_" + TimeCommons.getCurrentTimeStamp()).toString();
        File directory = new File(outputFolder);
        if (!directory.exists()) {
            directory.mkdir();
        }
        log.info("Stworzono folder dla podzielonych danych Wavelo: [{}]", outputFolder);
    }
}
