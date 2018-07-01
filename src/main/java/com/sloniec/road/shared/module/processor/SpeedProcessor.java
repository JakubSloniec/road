package com.sloniec.road.shared.module.processor;

import static com.sloniec.road.shared.Context.getFilterSwitchRecordsPerRegion;
import static com.sloniec.road.shared.Context.getFilterValueRecordsPerRegion;
import static java.util.Arrays.asList;

import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.FileCommons;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.commons.SpeedCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import com.sloniec.road.shared.result.SingeSpeedResult;
import com.sloniec.road.shared.result.SpeedResult;
import java.util.List;

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

        List<Waypoint> waypoints = reader.getWaypoints(file);
        SpeedResult result = new SpeedResult(file, Context.getStep(), waypoints.get(0));

        List<Waypoint> beforeWaypoints = checker.getAllPointsInAreaAndAround(Context.getBeforeArea(), waypoints);
        result.getBeforeSpeeds().addAll(getSpeeds(beforeWaypoints));

        List<Waypoint> duringWaypoints = checker.getAllPointsInAreaAndAround(Context.getDuringArea(), waypoints);
        result.getDuringSpeeds().addAll(getSpeeds(duringWaypoints));

        List<Waypoint> afterWaypoints = checker.getAllPointsInAreaAndAround(Context.getAfterArea(), waypoints);
        result.getAfterSpeeds().addAll(getSpeeds(afterWaypoints));

        return asList(result);
    }

    @Override
    boolean shouldVerifyResult() {
        return getFilterSwitchRecordsPerRegion();
    }

    private List<SingeSpeedResult> getSpeeds(List<Waypoint> waypoints) {
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
