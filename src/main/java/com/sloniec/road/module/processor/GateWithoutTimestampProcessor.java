package com.sloniec.road.module.processor;

import com.sloniec.road.module.result.GateWithoutTimestampResult;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.Segment;
import com.sloniec.road.shared.gpxparser.modal.GPX;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;

import java.util.List;

import static com.sloniec.road.shared.commons.SegmentCommons.segmentIntersect;
import static com.sloniec.road.shared.commons.SegmentCommons.waypointsToSegments;
import static java.util.stream.Collectors.toList;

public class GateWithoutTimestampProcessor extends AbstractProcessor<GateWithoutTimestampResult> {

    public GateWithoutTimestampProcessor(GpxFileReader reader) {
        super(reader);
    }

    @Override
    List<GateWithoutTimestampResult> processFile(String file) {
        GPX gpx = reader.getGPX(file);
        List<Waypoint> waypoints = reader.getWaypoints(gpx);
        String startDate = reader.getComment(gpx);

        Segment gate = Context.getGate();

        return waypointsToSegments(waypoints)
                .filter(s -> segmentIntersect(gate, s))
                .map(s -> new GateWithoutTimestampResult(startDate, file))
                .collect(toList());
    }

    @Override
    boolean shouldVerifyResult() {
        return false;
    }

    @Override
    boolean verifyResult(GateWithoutTimestampResult result) {
        return false;
    }
}

