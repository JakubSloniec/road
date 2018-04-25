package com.sloniec.road.shared.gpxparser.modal;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrackSegment extends Extension {

    private ArrayList<Waypoint> waypoints;

    public void addWaypoint(Waypoint wp) {
        if (waypoints == null) {
            waypoints = new ArrayList<>();
        }
        waypoints.add(wp);
    }
}
