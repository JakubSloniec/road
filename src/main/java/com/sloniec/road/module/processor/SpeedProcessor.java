package com.sloniec.road.module.processor;

import static com.sloniec.road.shared.Context.getFilterSwitchRecordsPerRegion;
import static com.sloniec.road.shared.Context.getFilterValueRecordsPerRegion;
import static com.sloniec.road.shared.commons.InterpolationCommons.generateResults;
import static com.sloniec.road.shared.commons.InterpolationCommons.interpolatedFunction;
import static java.util.Arrays.asList;

import com.sloniec.road.module.result.SingleSpeedResult;
import com.sloniec.road.module.result.SpeedResult;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.commons.TimeCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;

@Slf4j
public class SpeedProcessor extends AbstractProcessor<SpeedResult> {

    private PointInAreaCommons pointInAreaCommons = new PointInAreaCommons();

    public SpeedProcessor(GpxFileReader reader) {
        super(reader);
    }

    @Override
    public List<SpeedResult> process(List<String> files) {
        return super.process(files);
    }

    @Override
    List<SpeedResult> processFile(String file) {
        try {
            List<Waypoint> waypoints = reader.getWaypoints(file);
            SpeedResult result = new SpeedResult(file, Context.getStep(), waypoints.get(0));

            List<Waypoint> beforeWaypoints = pointInAreaCommons
                .getAllPointsInAreaAndAround(Context.getBeforeArea(), waypoints);
            result.getBeforeSpeeds().addAll(getSpeeds(beforeWaypoints));

            List<Waypoint> duringWaypoints = pointInAreaCommons
                .getAllPointsInAreaAndAround(Context.getDuringArea(), waypoints);
            result.getDuringSpeeds().addAll(getSpeeds(duringWaypoints));

            List<Waypoint> afterWaypoints = pointInAreaCommons
                .getAllPointsInAreaAndAround(Context.getAfterArea(), waypoints);
            result.getAfterSpeeds().addAll(getSpeeds(afterWaypoints));
            return asList(result);
        } catch (NonMonotonicSequenceException e) {
            log.error("Błąd interpolacji w pliku: [{}]", file);
            return null;
        }
    }

    @Override
    boolean shouldVerifyResult() {
        return getFilterSwitchRecordsPerRegion();
    }

    private List<SingleSpeedResult> getSpeeds(List<Waypoint> waypoints) throws NonMonotonicSequenceException {
        UnivariateFunction function = interpolatedFunction(waypoints);
        Double begin = TimeCommons.halfBetween(waypoints.get(0), waypoints.get(1));
        Double end = TimeCommons.halfBetween(waypoints.get(waypoints.size() - 2), waypoints.get(waypoints.size() - 1));
        return generateResults(function, begin, end);
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
