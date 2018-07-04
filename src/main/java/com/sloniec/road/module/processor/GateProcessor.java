package com.sloniec.road.module.processor;

import static com.sloniec.road.shared.commons.SegmentCommons.segmentIntersect;
import static java.util.stream.Collectors.toList;

import com.sloniec.road.module.result.GateResult;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.FileCommons;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.Segment;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;
import java.util.stream.IntStream;

public class GateProcessor extends AbstractProcessor<GateResult> {

    public GateProcessor(GpxFileReader reader) {
        super(reader);
    }

    @Override
    List<GateResult> processFile(String file) {
        FileCommons.checkIfIsFile(file);

        List<Waypoint> waypoints = reader.getWaypoints(file);

        Segment gate = Context.getGate();

        List<GateResult> results = IntStream.range(1, waypoints.size())
            .mapToObj(i -> new Segment(waypoints.get(i - 1), waypoints.get(i)))
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
