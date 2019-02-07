package com.sloniec.road.module.result;

import com.sloniec.road.framework.interf.IResult;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RectangleResult implements IResult {
    private String file;
    private Waypoint beginningWaypoint;
    private Double step;
    private List<SingleRectangleResult> speeds;

    public RectangleResult(String file, Waypoint beginningWaypoint, Double step) {
        this.file = file;
        this.beginningWaypoint = beginningWaypoint;
        this.step = step;
        this.speeds = new ArrayList<>();
    }
}
