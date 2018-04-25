
package com.sloniec.road.shared.gpxparser.modal;

import java.util.ArrayList;
import java.util.HashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Route extends Extension {

    private String name;
    private String comment;
    private String description;
    private String src;
    private HashSet<Link> links;
    private Integer number;

    private String type;
    private ArrayList<Waypoint> routePoints;

    public void addRoutePoint(Waypoint waypoint) {
        if (routePoints == null) {
            routePoints = new ArrayList<>();
        }
        routePoints.add(waypoint);
    }

    public void addLink(Link link) {
        if (links == null) {
            links = new HashSet<>();
        }
        links.add(link);
    }
}
