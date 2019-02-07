package com.sloniec.road.module.processor;

import static com.sloniec.road.shared.commons.SegmentCommons.segmentIntersect;
import static com.sloniec.road.shared.commons.SegmentCommons.waypointsToSegments;
import static java.util.stream.Collectors.toList;

import com.sloniec.road.module.result.GateResult;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.Segment;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;

public class GateProcessor extends AbstractProcessor<GateResult> {

    public GateProcessor(GpxFileReader reader) {
        super(reader);
    }

    @Override
    List<GateResult> processFile(String file) {
        List<Waypoint> waypoints = reader.getWaypoints(file);

        Segment gate = Context.getGate();

        List<GateResult> results = waypointsToSegments(waypoints)
            .filter(s -> segmentIntersect(gate, s))
            .map(GateResult::new)
            .collect(toList());

        results.forEach(r -> r.setFileName(file));

        return results;
    }

    @Override
    boolean shouldVerifyResult() {
        return false;
    }

    @Override
    boolean verifyResult(GateResult result) {
        return false;
    }
}
