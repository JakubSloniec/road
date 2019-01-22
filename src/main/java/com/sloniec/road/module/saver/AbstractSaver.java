package com.sloniec.road.module.saver;

import com.sloniec.road.framework.interf.IResult;
import com.sloniec.road.framework.interf.ISaver;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.CSVCommons;
import com.sloniec.road.shared.commons.TimeCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractSaver<T extends IResult> implements ISaver<T> {

    @Override
    public void save(List<T> results) {
        if (results.isEmpty()) {
            log.info("Brak wyników do zapisania");
        } else {
            List<List<String>> stringResults = resultsToString(results);
            String outputFile = outputFile();
            CSVCommons.toCSV(outputFile, header(), stringResults);
            log.info("Wyniki zostaly zapisane do pliku: [{}]", outputFile);
        }
    }

    @Override
    public List<List<String>> resultsToString(List<T> results) {
        return results.stream()
            .map(this::resultToString)
            .flatMap(List::stream)
            .collect(Collectors.toList());
    }

    protected abstract List<List<String>> resultToString(T result);

    protected abstract List<String> header();

    @Override
    public String outputFile() {
        Path outputFolder = Paths.get(Context.getRootPath(), "wyniki");
        try {
            outputFolder = Files.createDirectories(outputFolder);
        } catch (IOException e) {
            log.error("Wystapił problem z utworzeniem ścieżki: [{}]", outputFolder);
            System.exit(0);
        }

        String fileName = "wynik_" + Context.getDataSource() + "_" + Context.getProcessingType() + "_" + TimeCommons
            .getCurrentTimeStamp() + ".csv";
        return Paths.get(outputFolder.toString(), fileName).toString();
    }

    protected List<String> waypointToString(Waypoint waypoint) {
        List<String> list = new ArrayList<>();
        list.add(TimeCommons.dateToString(waypoint.getTime()));
        list.add(Double.toString(waypoint.getLatitude()));
        list.add(Double.toString(waypoint.getLongitude()));
        return list;
    }
}
