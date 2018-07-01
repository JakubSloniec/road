package com.sloniec.road.shared.module.saver;

import com.sloniec.road.framework.IResult;
import com.sloniec.road.framework.ISaver;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.CSVCommons;
import com.sloniec.road.shared.commons.TimeCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractSaver<T extends IResult> implements ISaver<T> {

    @Override
    public void save(List<T> results) {
        List<List<String>> stringResults = resultsToString(results);
        String outputFile = outputFile();
        CSVCommons.toCSV(outputFile, header(), stringResults);
        System.out.println("Wyniki zostaly zapisane do pliku: " + outputFile);
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
        String outputFolder = Context.getRootPath() + "\\wyniki";
        File directory = new File(outputFolder);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return outputFolder + "\\wynik_" + Context.getDataSource() + "_" + Context.getProcessingType() + "_" + TimeCommons.getCurrentTimeStamp() + ".csv";
    }

    protected List<String> waypointToString(Waypoint waypoint) {
        List<String> list = new ArrayList<>();
        list.add(TimeCommons.dateToString(waypoint.getTime()));
        list.add(Double.toString(waypoint.getLatitude()));
        list.add(Double.toString(waypoint.getLongitude()));
        return list;
    }
}
