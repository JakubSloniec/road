package com.sloniec.road.shared.commons;

import static com.sloniec.road.shared.commons.AreaCommons.isPointInArea;

import com.sloniec.road.shared.WaypointComparator;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PointInAreaCommons {

    public Waypoint getFirstPointInArea(Area area, List<Waypoint> waypoints) {
        return waypoints.stream()
            .filter(waypoint -> isPointInArea(area, waypoint))
            .findFirst()
            .orElse(null);
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
}
