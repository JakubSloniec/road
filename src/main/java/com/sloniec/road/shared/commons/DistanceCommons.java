package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;
import java.util.stream.IntStream;
import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalPosition;

public class DistanceCommons {
    private static final GeodeticCalculator geoCalc = new GeodeticCalculator();
    private static final Ellipsoid reference = Ellipsoid.WGS84;

    public static double distance(Waypoint start, Waypoint end) {
        return geoCalc.calculateGeodeticCurve(reference, convert(end), convert(start)).getEllipsoidalDistance();
    }

    private static GlobalPosition convert(Waypoint waypoint) {
        return new GlobalPosition(waypoint.getLatitude(), waypoint.getLongitude(), waypoint.getElevation());
    }

    private static double summedDistance(List<Waypoint> waypoints) {
        double[] distance = {0d};
        IntStream.range(0, waypoints.size() - 1)
            .forEach(i -> distance[0] += distance(waypoints.get(i), waypoints.get(i + 1)));
        return distance[0];
    }
}
