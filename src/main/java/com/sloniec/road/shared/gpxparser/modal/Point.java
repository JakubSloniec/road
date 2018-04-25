
package com.sloniec.road.shared.gpxparser.modal;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Point {

    private double latitude;
    private double longitude;

    private double elevation;
    private Date time;

    public Point(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
