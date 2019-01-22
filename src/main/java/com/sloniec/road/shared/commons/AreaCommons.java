package com.sloniec.road.shared.commons;

import static com.sloniec.road.shared.commons.GPXCommons.x;
import static com.sloniec.road.shared.commons.GPXCommons.y;
import static com.sloniec.road.shared.commons.SegmentCommons.segmentIntersect;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;

public class AreaCommons {

    public static boolean isSegmentIntersectingWithArea(Area a, Segment s) {
        return segmentIntersect(new Segment(a.a, a.b), s)
            || segmentIntersect(new Segment(a.b, a.c), s)
            || segmentIntersect(new Segment(a.c, a.d), s)
            || segmentIntersect(new Segment(a.d, a.a), s);
    }

    public static boolean isPointInArea(Area q, Waypoint p) {
        return comparePointToEdge(q.a, q.b, p)
            && comparePointToEdge(q.b, q.c, p)
            && comparePointToEdge(q.c, q.d, p)
            && comparePointToEdge(q.d, q.a, p);
    }

    private static boolean comparePointToEdge(Waypoint a, Waypoint b, Waypoint p) {
        double castPointOnEdge = ((x(a) - x(b)) * (y(p) - y(b))) - ((y(a) - y(b)) * (x(p) - x(b)));
        return castPointOnEdge <= 0; // anticlockwise
    }
}
