package com.sloniec.road.shared.commons;

import static com.sloniec.road.shared.commons.GPXCommons.x;
import static com.sloniec.road.shared.commons.GPXCommons.y;
import static java.lang.Math.max;
import static java.lang.Math.min;

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

    public boolean segmentIntersect(Segment a, Segment b) {
        return checkIfIntersect(a.getP1(), a.getP2(), b.getP1(), b.getP2());
    }

    public boolean segmentIntersect(Segment a, Waypoint p3, Waypoint p4) {
        return checkIfIntersect(a.getP1(), a.getP2(), p3, p4);
    }

    public boolean segmentIntersect(Waypoint p1, Waypoint p2, Segment b) {
        return checkIfIntersect(p1, p2, b.getP1(), b.getP2());
    }

    public boolean segmentIntersect(Waypoint p1, Waypoint p2, Waypoint p3, Waypoint p4) {
        return checkIfIntersect(p1, p2, p3, p4);
    }

    private static GlobalPosition convert(Waypoint waypoint) {
        return new GlobalPosition(waypoint.getLatitude(), waypoint.getLongitude(), waypoint.getElevation());
    }

    /**
     * Check wether the segments A = |P1P2| and B = |P3P4| intersects
     */
    private boolean checkIfIntersect(Waypoint p1, Waypoint p2, Waypoint p3, Waypoint p4) {
        double S_1 = scalarProduct(p1, p3, p2);
        double S_2 = scalarProduct(p1, p4, p2);
        double S_3 = scalarProduct(p3, p1, p4);
        double S_4 = scalarProduct(p3, p2, p4);
        if (((S_1 > 0 && S_2 < 0) || (S_1 < 0 && S_2 > 0)) && ((S_3 < 0 && S_4 > 0) || (S_3 > 0 && S_4 < 0))) {
            return true;
        } else if (S_1 == 0 && isBetween(p1, p2, p3)) {
            return true;
        } else if (S_2 == 0 && isBetween(p1, p2, p4)) {
            return true;
        } else if (S_3 == 0 && isBetween(p3, p4, p1)) {
            return true;
        } else if (S_4 == 0 && isBetween(p3, p4, p2)) {
            return true;
        } else {
            return false;
        }
    }

    private double scalarProduct(Waypoint p1, Waypoint p2, Waypoint p3) {
        return (x(p2) - x(p1)) * (y(p3) - y(p1)) - (x(p3) - x(p1)) * (y(p2) - y(p1));
    }

    /**
     * Check wether point P3 is between P1 and P2. Assumes that P1, P2 and P3 are collinear
     */
    private boolean isBetween(Waypoint p1, Waypoint p2, Waypoint p3) {
        return (min(x(p1), x(p2)) <= x(p3)) && (x(p3) <= max(x(p1), x(p2)));
    }

    private static double summedDistance(List<Waypoint> waypoints) {
        double[] distance = {0d};
        IntStream.range(0, waypoints.size() - 1)
            .forEach(i -> distance[0] += distance(waypoints.get(i), waypoints.get(i + 1)));
        return distance[0];
    }
}
