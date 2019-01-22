package com.sloniec.road.module.processor;

import com.sloniec.road.module.result.RectangleSpeedResult;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;

public class RectangleProcessor extends AbstractProcessor<RectangleSpeedResult> {

    public RectangleProcessor(GpxFileReader reader) {
        super(reader);
    }

    @Override
    List<RectangleSpeedResult> processFile(String file) {
        List<Waypoint> waypoints = reader.getWaypoints(file);
        return null;
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
