package com.sloniec.road.shared.gpxparser.modal;

import java.util.HashSet;

/**
 * This class holds gpx information from a &lt;gpx&gt; node. <br>
 * <p>
 * GPX specification for this tag:
 * </p>
 * <code>
 * &lt;gpx version="1.1" creator=""xsd:string [1]"&gt;<br>
 * &nbsp;&nbsp;&nbsp;&lt;metadata&gt; xsd:string &lt;/metadata&gt; [0..1]<br>
 * &nbsp;&nbsp;&nbsp;&lt;wpt&gt; xsd:string &lt;/wpt&gt; [0..1]<br>
 * &nbsp;&nbsp;&nbsp;&lt;rte&gt; xsd:string &lt;/rte&gt; [0..1]<br>
 * &nbsp;&nbsp;&nbsp;&lt;trk&gt; xsd:string &lt;/trk&gt; [0..1]<br>
 * &nbsp;&nbsp;&nbsp;&lt;extensions&gt; extensionsType &lt;/extensions&gt; [0..1]<br>
 * &lt;/gpx&gt;<br>
 * </code>
 */
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

    /**
     * Returns the creator of this gpx object
     *
     * @return A String representing the creator of a gpx object
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Setter for gpx creator property. This maps to <i>creator</i> attribute
     * value.
     *
     * @param creator A String representing the creator of a gpx file.
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * Getter for the list of routes from a gpx object
     *
     * @return a HashSet of {@link Route}
     */
    public HashSet<Route> getRoutes() {
        return routes;
    }

    /**
     * Setter for the list of routes from a gpx object
     *
     * @param routes a HashSet of {@link Route}
     */
    public void setRoutes(HashSet<Route> routes) {
        this.routes = routes;
    }

    /**
     * Getter for the list of Tracks from a gpx objecty
     *
     * @return a HashSet of {@link Track}
     */
    public HashSet<Track> getTracks() {
        return tracks;
    }

    /**
     * Setter for the list of tracks from a gpx object
     *
     * @param tracks a HashSet of {@link Track}
     */
    public void setTracks(HashSet<Track> tracks) {
        this.tracks = tracks;
    }

    /**
     * Returns the version of a gpx object
     *
     * @return A String representing the version of this gpx object
     */
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Getter for the list of waypoints from a gpx objecty
     *
     * @return a HashSet of {@link Waypoint}
     */
    public HashSet<Waypoint> getWaypoints() {
        return waypoints;
    }

    /**
     * Setter for the list of waypoints from a gpx object
     *
     * @param waypoints a HashSet of {@link Waypoint}
     */
    public void setWaypoints(HashSet<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

//    @Override
//    public String toString() {
//        String ret = "GPX{waypoints=[";
//        for (Waypoint w : waypoints) {
//            ret += w;
//        }
//        ret += "]}";
//        return ret;
//    }

    @Override
    public String toString() {
        return "GPX{" +
                "creator='" + creator + '\'' +
                ", version='" + version + '\'' +
                ", metadata=" + metadata +
                ", routes=" + routes +
                ", tracks=" + tracks +
                ", waypoints=" + waypoints +
                '}';
    }
}
