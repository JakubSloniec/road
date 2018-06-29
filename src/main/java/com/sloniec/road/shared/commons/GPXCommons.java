package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;

public class GPXCommons {
    public static double x(Waypoint p) {
        return p.getLongitude();
    }

    public static double y(Waypoint p) {
        return p.getLatitude();
    }
}
