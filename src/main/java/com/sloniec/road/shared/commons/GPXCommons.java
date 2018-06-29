package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;

public class GPXCommons {
    public static double x(Waypoint p) {
        return p.getLongitude();
    }

    public static double y(Waypoint p) {
        return p.getLatitude();
    }

    public static Waypoint stringToPoint(String s) {
        String[] split = s.split(",");

        if (split.length != 2) {
            throw new IllegalArgumentException("Incorrect value when creating waypoint: " + s);
        }

        double lat = Double.valueOf(split[0].trim());
        double lon = Double.valueOf(split[1].trim());
        return new Waypoint(lat, lon);
    }
}
