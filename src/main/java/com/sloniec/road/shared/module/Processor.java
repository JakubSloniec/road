package com.sloniec.road.shared.module;

import com.sloniec.road.framework.IProcessor;
import com.sloniec.road.shared.Params;
import com.sloniec.road.shared.commons.FileCommons;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.commons.SpeedCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import com.sloniec.road.shared.result.SingeSpeedResult;
import com.sloniec.road.shared.result.SpeedProcessingResult;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Processor implements IProcessor {

    private GpxFileReader reader;
    private PointInAreaCommons checker = new PointInAreaCommons();
    private int incorrectResults = 0;

    public Processor(GpxFileReader reader) {
        this.reader = reader;
    }

    @Override
    public List<SpeedProcessingResult> process(List<String> files) {
        System.out.println("Liczba danych do procesowania: " + files.size());

        List<SpeedProcessingResult> results = files.stream()
                .map(this::processFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (Params.getFilterSwitch()) {
            System.out.println("Liczba odrzucownych wynikow: " + incorrectResults);
        }
        return results;
    }

    private SpeedProcessingResult processFile(String file) {
        FileCommons.checkIfIsFile(file);

        List<Waypoint> waypoints = reader.getWaypoints(file);
        SpeedProcessingResult result = new SpeedProcessingResult(file, Params.getStep(), waypoints.get(0));

        List<Waypoint> beforeWaypoints = checker.getAllPointsInAreaAndAround(Params.getBeforeArea(), waypoints);
        result.getBeforeSpeeds().addAll(getSpeeds(beforeWaypoints));

        List<Waypoint> duringWaypoints = checker.getAllPointsInAreaAndAround(Params.getDuringArea(), waypoints);
        result.getDuringSpeeds().addAll(getSpeeds(duringWaypoints));

        List<Waypoint> afterWaypoints = checker.getAllPointsInAreaAndAround(Params.getAfterArea(), waypoints);
        result.getAfterSpeeds().addAll(getSpeeds(afterWaypoints));

        return verifyResult(result);
    }

    private List<SingeSpeedResult> getSpeeds(List<Waypoint> waypoints) {
        SpeedCommons commons = new SpeedCommons(waypoints, Params.getStep());
        return commons.calculateSpeeds();
    }

    private SpeedProcessingResult verifyResult(SpeedProcessingResult result) {
        if (Params.getFilterSwitch()) {
            Double maxResults = Params.getFilterValue();
            boolean correctResultNumber = result.getBeforeSpeeds().size() < maxResults
                    && result.getDuringSpeeds().size() < maxResults
                    && result.getDuringSpeeds().size() < maxResults;

            if (correctResultNumber) {
                return result;
            } else {
                incorrectResults++;
                return null;
            }
        }
        return result;
    }
}
