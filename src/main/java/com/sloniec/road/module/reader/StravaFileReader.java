package com.sloniec.road.module.reader;

import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.gpxparser.modal.GPX;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;

public class StravaFileReader extends GpxFileReader {

    @Override
    public List<Waypoint> getWaypoints(GPX gpx) {
        return gpx.getTracks().iterator().next().getTrackSegments().iterator().next().getWaypoints();
    }
}
