package com.sloniec.road.shared.module.processor;

import static com.sloniec.road.shared.Context.getFilterSwitchRecordsPerRegion;
import static com.sloniec.road.shared.Context.getFilterValueRecordsPerRegion;

import com.sloniec.road.framework.IProcessor;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.FileCommons;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.commons.SpeedCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import com.sloniec.road.shared.result.SingeSpeedResult;
import com.sloniec.road.shared.result.SpeedResult;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SpeedProcessor implements IProcessor {

    private GpxFileReader reader;
    private PointInAreaCommons checker = new PointInAreaCommons();
    private int incorrectResults = 0;

    public SpeedProcessor(GpxFileReader reader) {
        this.reader = reader;
    }

    @Override
    public List<SpeedResult> process(List<String> files) {
        System.out.println("Liczba danych do procesowania: " + files.size());

        List<SpeedResult> results = files.stream()
            .map(this::processFile)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        if (getFilterSwitchRecordsPerRegion()) {
            System.out.println("Liczba odrzucownych wynikow: " + incorrectResults);
        }
        return results;
    }

    private SpeedResult processFile(String file) {
        FileCommons.checkIfIsFile(file);

        List<Waypoint> waypoints = reader.getWaypoints(file);
        SpeedResult result = new SpeedResult(file, Context.getStep(), waypoints.get(0));

        List<Waypoint> beforeWaypoints = checker.getAllPointsInAreaAndAround(Context.getBeforeArea(), waypoints);
        result.getBeforeSpeeds().addAll(getSpeeds(beforeWaypoints));

        List<Waypoint> duringWaypoints = checker.getAllPointsInAreaAndAround(Context.getDuringArea(), waypoints);
        result.getDuringSpeeds().addAll(getSpeeds(duringWaypoints));

        List<Waypoint> afterWaypoints = checker.getAllPointsInAreaAndAround(Context.getAfterArea(), waypoints);
        result.getAfterSpeeds().addAll(getSpeeds(afterWaypoints));

        return verifyResult(result);
    }

    private List<SingeSpeedResult> getSpeeds(List<Waypoint> waypoints) {
        SpeedCommons commons = new SpeedCommons(waypoints, Context.getStep());
        return commons.calculateSpeeds();
    }

    private SpeedResult verifyResult(SpeedResult result) {
        if (getFilterSwitchRecordsPerRegion()) {
            Double maxResults = getFilterValueRecordsPerRegion();
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
