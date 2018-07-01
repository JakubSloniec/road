package com.sloniec.road.shared.commons;

import static com.sloniec.road.shared.commons.GPXCommons.x;
import static com.sloniec.road.shared.commons.GPXCommons.y;
import static java.lang.Math.max;
import static java.lang.Math.min;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;

public class SegmentCommons {
    public static boolean segmentIntersect(Segment a, Segment b) {
        return checkIfIntersect(a.getP1(), a.getP2(), b.getP1(), b.getP2());
    }

    public static boolean segmentIntersect(Segment a, Waypoint p3, Waypoint p4) {
        return checkIfIntersect(a.getP1(), a.getP2(), p3, p4);
    }

    public static boolean segmentIntersect(Waypoint p1, Waypoint p2, Segment b) {
        return checkIfIntersect(p1, p2, b.getP1(), b.getP2());
    }

    public static boolean segmentIntersect(Waypoint p1, Waypoint p2, Waypoint p3, Waypoint p4) {
        return checkIfIntersect(p1, p2, p3, p4);
    }

    /**
     * Check wether the segments A = |P1P2| and B = |P3P4| intersects
     */
    private static boolean checkIfIntersect(Waypoint p1, Waypoint p2, Waypoint p3, Waypoint p4) {
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

    private static double scalarProduct(Waypoint p1, Waypoint p2, Waypoint p3) {
        return (x(p2) - x(p1)) * (y(p3) - y(p1)) - (x(p3) - x(p1)) * (y(p2) - y(p1));
    }

    /**
     * Check wether point P3 is between P1 and P2. Assumes that P1, P2 and P3 are collinear
     */
    private static boolean isBetween(Waypoint p1, Waypoint p2, Waypoint p3) {
        return (min(x(p1), x(p2)) <= x(p3)) && (x(p3) <= max(x(p1), x(p2)));
    }
}
