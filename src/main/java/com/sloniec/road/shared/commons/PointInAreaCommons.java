package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.Area;
import com.sloniec.road.shared.WaypointComparator;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PointInAreaCommons {

    public Waypoint getFirstPointInArea(Area area, List<Waypoint> waypoints) {
        return waypoints.stream()
                .filter(waypoint -> isPointInArea(area, waypoint))
                .findFirst().orElse(null);
    }

    public List<Waypoint> getAllPointsInAreaAndAround(Area area, List<Waypoint> waypoints) {
        List<Waypoint> selectedWaypoints = waypoints.stream()
                .filter(waypoint -> isPointInArea(area, waypoint))
                .sorted(new WaypointComparator())
                .collect(Collectors.toList());
        return addBeforeAndAfterPoints(selectedWaypoints, waypoints);
    }

    private List<Waypoint> addBeforeAndAfterPoints(List<Waypoint> selectedWaypoints, List<Waypoint> allWaypoints) {
        List<Waypoint> result = new ArrayList<>();
        Waypoint before = null;
        Waypoint after = null;
        int firstIndex;
        int lastIndex;
        if (selectedWaypoints.size() == 1) {
            firstIndex = allWaypoints.indexOf(selectedWaypoints.get(0));
//            if (firstIndex == 0) {
//                return allWaypoints.subList(0, 3);
//            }
            lastIndex = firstIndex;
        } else {
            firstIndex = allWaypoints.indexOf(selectedWaypoints.get(0));
            lastIndex = allWaypoints.indexOf(selectedWaypoints.get(selectedWaypoints.size() - 1));
        }

        if (firstIndex != 0) {
            before = allWaypoints.get(firstIndex - 1);
        }
        if (lastIndex != allWaypoints.size() - 1) {
            after = allWaypoints.get(lastIndex + 1);
        }

        if (before != null) {
            result.add(before);
        }
        result.addAll(selectedWaypoints);
        if (after != null) {
            result.add(after);
        }
        return result;
    }

    private boolean isPointInArea(Area q, Waypoint p) {
        return comparePointToEdge(q.a, q.b, p)
                && comparePointToEdge(q.b, q.c, p)
                && comparePointToEdge(q.c, q.d, p)
                && comparePointToEdge(q.d, q.a, p);
    }

    private boolean comparePointToEdge(Waypoint a, Waypoint b, Waypoint p) {
        double castPointOnEdge = ((x(a) - x(b)) * (y(p) - y(b))) - ((y(a) - y(b)) * (x(p) - x(b)));
        return castPointOnEdge <= 0; // anticlockwise
    }

    private double x(Waypoint p) {
        return p.getLongitude();
    }

    private double y(Waypoint p) {
        return p.getLatitude();
    }

/*
    // alternative implementation of point in area
    public static boolean inside(Waypoint geo, List<Waypoint> points) {
        int i, j;
        boolean c = false;
        for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
            if (((points.get(i).getLongitude() > geo.getLongitude()) != (points.get(j).getLongitude() > geo.getLongitude())) &&
                    (geo.getLatitude() < (points.get(j).getLatitude() - points.get(i).getLatitude()) * (geo.getLongitude() - points.get(i).getLongitude()) / (points.get(j).getLongitude() - points.get(i).getLongitude()) + points.get(i).getLatitude())) {
                c = !c;
            }
        }
        return c;
    }
*/
}
