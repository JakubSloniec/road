package com.sloniec.road.shared.gpxparser.modal;

import com.sloniec.road.shared.gpxparser.type.Fix;
import java.util.Date;
import java.util.HashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"latitude", "longitude", "time"})
public class Waypoint extends Extension {

    private double latitude;
    private double longitude;

    private double elevation;
    private Date time;
    private double magneticVariation;
    private double geoIdHeight;
    private String name;
    private String comment;
    private String description;
    private String src;
    private HashSet<Link> links;
    /**
     * Symbol
     */
    private String sym;
    private String type;
    private Fix fix;
    private int sat;
    /**
     * Horizontal dilution of precision.
     */
    private double hdop;
    /**
     * Vertical dilution of precision.
     */
    private double vdop;
    /**
     * Position dilution of precision.
     */
    private double pdop;

    private double ageOfGpsData;
    private int dGpsStationId;

    public Waypoint(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void addLink(Link link) {
        if (links == null) {
            links = new HashSet<>();
        }
        links.add(link);
    }
}
