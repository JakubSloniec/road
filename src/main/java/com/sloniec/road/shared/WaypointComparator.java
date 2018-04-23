package com.sloniec.road.shared;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;

import java.util.Comparator;

public class WaypointComparator implements Comparator<Waypoint> {
    @Override
    public int compare(Waypoint o1, Waypoint o2) {
        return o1.getTime().compareTo(o2.getTime());
    }
}
