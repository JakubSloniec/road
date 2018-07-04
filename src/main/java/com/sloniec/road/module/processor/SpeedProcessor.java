package com.sloniec.road.module.processor;

import static com.sloniec.road.shared.Context.getFilterSwitchRecordsPerRegion;
import static com.sloniec.road.shared.Context.getFilterValueRecordsPerRegion;
import static java.util.Arrays.asList;

import com.sloniec.road.module.result.SingeSpeedResult;
import com.sloniec.road.module.result.SpeedResult;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.FileCommons;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.commons.SpeedCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;

public class SpeedProcessor extends AbstractProcessor<SpeedResult> {

    private PointInAreaCommons checker = new PointInAreaCommons();

    public SpeedProcessor(GpxFileReader reader) {
        super(reader);
    }

    @Override
    public List<SpeedResult> process(List<String> files) {
        return super.process(files);
    }

    @Override
    List<SpeedResult> processFile(String file) {
        FileCommons.checkIfIsFile(file);
        try {
            List<Waypoint> waypoints = reader.getWaypoints(file);
            SpeedResult result = new SpeedResult(file, Context.getStep(), waypoints.get(0));

            List<Waypoint> beforeWaypoints = checker.getAllPointsInAreaAndAround(Context.getBeforeArea(), waypoints);
            result.getBeforeSpeeds().addAll(getSpeeds(beforeWaypoints));

            List<Waypoint> duringWaypoints = checker.getAllPointsInAreaAndAround(Context.getDuringArea(), waypoints);
            result.getDuringSpeeds().addAll(getSpeeds(duringWaypoints));

            List<Waypoint> afterWaypoints = checker.getAllPointsInAreaAndAround(Context.getAfterArea(), waypoints);
            result.getAfterSpeeds().addAll(getSpeeds(afterWaypoints));
            return asList(result);
        } catch (NonMonotonicSequenceException e) {
            System.out.println("Błąd interpolacji w pliku: " + file);
            return null;
        }
    }

    @Override
    boolean shouldVerifyResult() {
        return getFilterSwitchRecordsPerRegion();
    }

    private List<SingeSpeedResult> getSpeeds(List<Waypoint> waypoints) throws NonMonotonicSequenceException {
        SpeedCommons commons = new SpeedCommons(waypoints, Context.getStep());
        return commons.calculateSpeeds();
    }

    @Override
    boolean verifyResult(SpeedResult result) {
        if (shouldVerifyResult()) {
            Double maxResults = getFilterValueRecordsPerRegion();
            return result.getBeforeSpeeds().size() < maxResults
                && result.getDuringSpeeds().size() < maxResults
                && result.getDuringSpeeds().size() < maxResults;
        }
        return false;
    }
}
