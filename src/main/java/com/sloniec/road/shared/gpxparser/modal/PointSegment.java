package com.sloniec.road.shared.gpxparser.modal;

import java.util.ArrayList;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PointSegment {

    private ArrayList<Point> points;

    public void addPoint(Point point) {
        if (points == null) {
            points = new ArrayList<>();
        }
        points.add(point);
    }
}
