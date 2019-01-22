package com.sloniec.road.shared.commons;

import static com.sloniec.road.shared.commons.GPXCommons.stringToPoint;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.Arrays;
import java.util.List;

public class Area {

    public Waypoint a;
    public Waypoint b;
    public Waypoint c;
    public Waypoint d;

    public Area(Waypoint a, Waypoint b, Waypoint c, Waypoint d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public Area(String a, String b, String c, String d) {
        this(stringToPoint(a), stringToPoint(b), stringToPoint(c), stringToPoint(d));
    }

    public List<Waypoint> toList() {
        return Arrays.asList(a, b, c, d);
    }

    @Override
    public String toString() {
        return "Area{" +
            "a=" + a +
            ", b=" + b +
            ", c=" + c +
            ", d=" + d +
            '}';
    }
}
