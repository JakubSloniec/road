package com.sloniec.road.module.processor;

import static com.sloniec.road.shared.commons.InterpolationCommons.generateResults;
import static com.sloniec.road.shared.commons.InterpolationCommons.interpolatedFunction;
import static com.sloniec.road.shared.commons.SegmentCommons.waypointsToSegments;
import static com.sloniec.road.shared.commons.TimeCommons.getTime;
import static com.sloniec.road.shared.commons.TimeCommons.halfBetween;
import static java.util.Arrays.asList;

import com.sloniec.road.module.result.RectangleResult;
import com.sloniec.road.module.result.SingleRectangleResult;
import com.sloniec.road.module.result.SingleSpeedResult;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.Area;
import com.sloniec.road.shared.commons.AreaCommons;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.commons.Segment;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;

@Slf4j
public class RectangleProcessor extends AbstractProcessor<RectangleResult> {

    private PointInAreaCommons pointInAreaCommons = new PointInAreaCommons();

    public RectangleProcessor(GpxFileReader reader) {
        super(reader);
    }

    @Override
    List<RectangleResult> processFile(String file) {
        List<Waypoint> waypoints = reader.getWaypoints(file);
        List<Waypoint> selected = selectWaypoints(waypoints);

        RectangleResult result = new RectangleResult(file, selected.get(0), Context.getStep());
        try {
            result.getSpeeds()
                .addAll(getSpeeds(
                    selected,
                    pointInAreaCommons.getAllPointsInArea(Context.getRectangle(), waypoints).isEmpty()
                ));
        } catch (NonMonotonicSequenceException e) {
            log.error("Błąd interpolacji w pliku: [{}]", file);
            return null;
        }
        return asList(result);
    }

    private List<Waypoint> selectWaypoints(List<Waypoint> waypoints) {
        List<Waypoint> pointsInArea = pointInAreaCommons.getAllPointsInArea(Context.getRectangle(), waypoints);
        if (pointsInArea.isEmpty()) {
            Area a = Context.getRectangle();
            Segment segment = waypointsToSegments(waypoints)
                .filter(s -> AreaCommons.isSegmentIntersectingWithArea(a, s))
                .findFirst().get();
            pointsInArea.addAll(asList(segment.getP1(), segment.getP2()));
        }

        return new ArrayList<>(pointInAreaCommons.addBeforeAndAfterPoints(pointsInArea, waypoints));
    }

    private List<SingleRectangleResult> getSpeeds(List<Waypoint> waypoints, Boolean hasPointsInArea)
        throws NonMonotonicSequenceException {
        UnivariateFunction function = interpolatedFunction(waypoints);

        Double begin;
        Double end;
        if (hasPointsInArea) {
            begin = halfBetween(waypoints.get(0), waypoints.get(1));
            end = halfBetween(waypoints.get(waypoints.size() - 2), waypoints.get(waypoints.size() - 1));
        } else {
            begin = getTime(waypoints.get(1));
            end = getTime(waypoints.get(2));
        }

        List<SingleSpeedResult> singleSpeedResults = generateResults(function, begin, end);
        List<SingleRectangleResult> singleRectangleResults = new ArrayList<>();
        singleRectangleResults.add(new SingleRectangleResult(singleSpeedResults.get(0)));
        singleSpeedResults
            .stream()
            .skip(1)
            .map(result -> {
                int index = singleSpeedResults.indexOf(result);
                Double v2 = result.getValue();
                Double v1 = singleRectangleResults.get(index - 1).getValue();
                double dt = Context.getStep() * 1000;
                Double acceleration = (v2 - v1) / dt;
                return new SingleRectangleResult(v2, result.getTime(), acceleration);
            })
            .forEach(singleRectangleResults::add);
        return singleRectangleResults;
    }

    @Override
    boolean shouldVerifyResult() {
        return false;
    }

    @Override
    boolean verifyResult(RectangleResult result) {
        return false;
    }
}
