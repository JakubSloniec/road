package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            log.error("Niepoprawna wartosc podana do tworzenia punktu: [{}]", s);
            System.exit(0);
        }

        double lat = Double.valueOf(split[0].trim());
        double lon = Double.valueOf(split[1].trim());
        return new Waypoint(lat, lon);
    }
}
