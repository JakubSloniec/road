package com.sloniec.road.module.wavelo;

import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.gpxparser.modal.GPX;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;

import java.util.List;

public class WaveloFileReader extends GpxFileReader {

    @Override
    public List<Waypoint> getWaypoints(GPX gpx) {
        return gpx.getTracks().iterator().next().getTrackSegments().iterator().next().getWaypoints();
    }
}
