package com.sloniec.road.shared.gpxparser.modal;

import java.util.HashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class holds gpx information from a &lt;gpx&gt; node. <br>
 * <p>
 * GPX specification for this tag:
 * </p>
 * <code>
 * &lt;gpx version="1.1" creator=""xsd:string [1]"&gt;<br> &nbsp;&nbsp;&nbsp;&lt;metadata&gt; xsd:string &lt;/metadata&gt; [0..1]<br> &nbsp;&nbsp;&nbsp;&lt;wpt&gt; xsd:string
 * &lt;/wpt&gt; [0..1]<br> &nbsp;&nbsp;&nbsp;&lt;rte&gt; xsd:string &lt;/rte&gt; [0..1]<br> &nbsp;&nbsp;&nbsp;&lt;trk&gt; xsd:string &lt;/trk&gt; [0..1]<br>
 * &nbsp;&nbsp;&nbsp;&lt;extensions&gt; extensionsType &lt;/extensions&gt; [0..1]<br> &lt;/gpx&gt;<br>
 * </code>
 */

@Getter
@Setter
@ToString
public class GPX extends Extension {

    // Attributes
    private String creator;
    private String version = "1.1";

    // Nodes
    private Metadata metadata;
    private HashSet<Route> routes;
    private HashSet<Track> tracks;
    private HashSet<Waypoint> waypoints;

    public GPX() {
        waypoints = new HashSet<>();
        tracks = new HashSet<>();
        routes = new HashSet<>();
    }


    /**
     * Adds a new Route to a gpx object
     *
     * @param route a {@link Route}
     */
    public void addRoute(Route route) {
        if (routes == null) {
            routes = new HashSet<>();
        }
        routes.add(route);
    }

    /**
     * Adds a new track to a gpx object
     *
     * @param track a {@link Track}
     */
    public void addTrack(Track track) {
        if (tracks == null) {
            tracks = new HashSet<>();
        }
        tracks.add(track);
    }

    /**
     * Adds a new waypoint to a gpx object
     *
     * @param waypoint a {@link Waypoint}
     */
    public void addWaypoint(Waypoint waypoint) {
        if (waypoints == null) {
            waypoints = new HashSet<>();
        }
        waypoints.add(waypoint);

    }
}
