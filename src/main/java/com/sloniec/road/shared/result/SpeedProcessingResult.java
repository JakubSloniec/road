package com.sloniec.road.shared.result;

import com.sloniec.road.framework.IResult;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;

import java.util.ArrayList;
import java.util.List;

public class SpeedProcessingResult implements IResult {
    private String file;
    private Waypoint beginningWaypoint;
    private Double step;
    private List<SingeSpeedResult> beforeSpeeds;
    private List<SingeSpeedResult> duringSpeeds;
    private List<SingeSpeedResult> afterSpeeds;

    public SpeedProcessingResult(String file, Double step, Waypoint beginningWaypoint) {
        this.file = file;
        this.step = step;
        this.beginningWaypoint = beginningWaypoint;
        beforeSpeeds = new ArrayList<>();
        duringSpeeds = new ArrayList<>();
        afterSpeeds = new ArrayList<>();
    }

    public String getFile() {
        return file;
    }

    public List<SingeSpeedResult> getBeforeSpeeds() {
        return beforeSpeeds;
    }

    public List<SingeSpeedResult> getDuringSpeeds() {
        return duringSpeeds;
    }

    public List<SingeSpeedResult> getAfterSpeeds() {
        return afterSpeeds;
    }

    public Double getStep() {
        return step;
    }

    public Waypoint getBeginningWaypoint() {
        return beginningWaypoint;
    }

    public void setBeginningWaypoint(Waypoint beginningWaypoint) {
        this.beginningWaypoint = beginningWaypoint;
    }


    @Override
    public String toString() {
        return "SpeedProcessingResult{" +
            "file='" + file + '\'' +
            ", beginningWaypoint=" + beginningWaypoint +
            ", step=" + step +
            ", beforeSpeeds=" + beforeSpeeds +
            ", duringSpeeds=" + duringSpeeds +
            ", afterSpeeds=" + afterSpeeds +
            '}';
    }
}
