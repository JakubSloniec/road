package com.sloniec.road.shared.module;

import com.sloniec.road.framework.IResult;
import com.sloniec.road.framework.ISaver;
import com.sloniec.road.shared.Params;
import com.sloniec.road.shared.commons.CSVCommons;
import com.sloniec.road.shared.commons.TimeCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import com.sloniec.road.shared.result.SingeSpeedResult;
import com.sloniec.road.shared.result.SpeedProcessingResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Saver implements ISaver {
    @Override
    public void save(List<? extends IResult> results) {
        List<List<String>> stringResults = resultsToString(results);
        String outputFile = outputFile();
        CSVCommons.toCSV(outputFile, header(), stringResults);
        System.out.println("Wyniki zostaly zapisane do pliku: " + outputFile);
    }

    @Override
    public List<List<String>> resultsToString(List<? extends IResult> results) {
        List<List<String>> stringResults = new ArrayList<>();
        for (IResult result : results) {
            List<List<String>> stringResult = singleResultToString((SpeedProcessingResult) result);
            stringResults.addAll(stringResult);
        }
        return stringResults;
    }

    private List<List<String>> singleResultToString(SpeedProcessingResult result) {
        List<List<String>> stringResult = new ArrayList<>();

        List<String> preColumns = getPreColumns(result);

        stringResult.addAll(speedResultsToString("PRZED", result.getBeforeSpeeds(), preColumns));
        stringResult.addAll(speedResultsToString("W_TRAKCIE", result.getDuringSpeeds(), preColumns));
        stringResult.addAll(speedResultsToString("PO", result.getAfterSpeeds(), preColumns));

        return stringResult;
    }

    private List<List<String>> speedResultsToString(String place, List<SingeSpeedResult> speedResults, List<String> preColumns) {
        List<List<String>> all = new ArrayList<>();
        for (SingeSpeedResult speedResult : speedResults) {
            List<String> single = new ArrayList<>();
            single.addAll(preColumns);
            single.add(place);
            single.add(TimeCommons.dateToString(speedResult.getTime()));
            single.add(Double.toString(speedResult.getValue()));
            all.add(single);
        }
        return all;
    }

    private List<String> getPreColumns(SpeedProcessingResult result) {
        List<String> preColumns = new ArrayList<>();
        preColumns.addAll(waypointToString(result.getBeginningWaypoint()));
        preColumns.add(result.getFile());
        preColumns.add(Double.toString(result.getStep()));
        return preColumns;
    }

    private List<String> waypointToString(Waypoint waypoint) {
        List<String> list = new ArrayList<>();
        list.add(TimeCommons.dateToString(waypoint.getTime()));
        list.add(Double.toString(waypoint.getLatitude()));
        list.add(Double.toString(waypoint.getLongitude()));
        return list;
    }

    @Override
    public List<String> header() {
        List<String> header = new ArrayList<>();
        header.add("start czas");
        header.add("start lat");
        header.add("start lon");
        header.add("plik");
        header.add("krok");
        header.add("miejsce");
        header.add("czas");
        header.add("predkosc");
        return header;
    }

    @Override
    public String outputFile() {
        String outputFolder = Params.getRootPath() + "\\wyniki";
        File directory = new File(outputFolder);
        if (!directory.exists()) {
            directory.mkdir();
        }
        return outputFolder + "\\wynik_" + Params.getModuleType() + "_" + TimeCommons.getCurrentTimeStamp() + ".csv";
    }
}
