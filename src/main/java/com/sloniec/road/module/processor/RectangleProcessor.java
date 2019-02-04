package com.sloniec.road.module.processor;

import static com.sloniec.road.shared.commons.SegmentCommons.waypointsToSegments;
import static java.util.Arrays.asList;

import com.sloniec.road.module.result.RectangleSpeedResult;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.Area;
import com.sloniec.road.shared.commons.AreaCommons;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.commons.Segment;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RectangleProcessor extends AbstractProcessor<RectangleSpeedResult> {

    private PointInAreaCommons pointInAreaCommons = new PointInAreaCommons();

    public RectangleProcessor(GpxFileReader reader) {
        super(reader);
    }

    @Override
    List<RectangleSpeedResult> processFile(String file) {
        List<Waypoint> waypoints = reader.getWaypoints(file);

        List<Waypoint> selected = selectWaypoints(waypoints);
        log.debug("SELECTED: {} ", selected.stream().map(Waypoint::toString).collect(Collectors.joining(",\n")));

        // TODO process

        return null;
    }

    private List<Waypoint> selectWaypoints(List<Waypoint> waypoints) {
        List<Waypoint> selected = new ArrayList<>();
        List<Waypoint> pointsInArea = pointInAreaCommons.getAllPointsInArea(Context.getRectangle(), waypoints);
        if (pointsInArea.isEmpty()) {
            Area a = Context.getRectangle();
            Segment segment = waypointsToSegments(waypoints)
                .filter(s -> AreaCommons.isSegmentIntersectingWithArea(a, s))
                .findFirst().get();
            selected.addAll(
                pointInAreaCommons.addBeforeAndAfterPoints(asList(segment.getP1(), segment.getP2()), waypoints));
        } else {
            selected.addAll(pointInAreaCommons.addBeforeAndAfterPoints(pointsInArea, waypoints));
        }
        return selected;
    }

    @Override
    boolean shouldVerifyResult() {
        return false;
    }

    @Override
    boolean verifyResult(RectangleSpeedResult result) {
        return false;
    }
}
