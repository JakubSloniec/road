package com.sloniec.road.shared.commons;

import static com.sloniec.road.shared.commons.GPXCommons.x;
import static com.sloniec.road.shared.commons.GPXCommons.y;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SegmentCommons {

    public static boolean segmentIntersect(Segment s1, Segment s2) {
        return Line2D.linesIntersect(
            x(s1.getP1()), y(s1.getP1()),
            x(s1.getP2()), y(s1.getP2()),
            x(s2.getP1()), y(s2.getP1()),
            x(s2.getP2()), y(s2.getP2())
        );
    }

    public static Stream<Segment> waypointsToSegments(List<Waypoint> waypoints) {
        return IntStream
            .range(1, waypoints.size())
            .mapToObj(i ->
                new Segment(
                    waypoints.get(i - 1),
                    waypoints.get(i)
                )
            );
    }
}
