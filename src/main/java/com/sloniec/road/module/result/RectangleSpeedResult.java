package com.sloniec.road.module.result;

import com.sloniec.road.framework.interf.IResult;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RectangleSpeedResult implements IResult {
    private String file;
    private Waypoint beginningWaypoint;
    private Double step;
    private List<SingeSpeedResult> beforeSpeeds;

    public RectangleSpeedResult(String file, Double step, Waypoint beginningWaypoint) {
        this.file = file;
        this.step = step;
        this.beginningWaypoint = beginningWaypoint;
        beforeSpeeds = new ArrayList<>();
    }
}
