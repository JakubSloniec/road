package com.sloniec.road.shared.result;

import java.util.Date;

public class SingeSpeedResult {

    private Double value;
    private Date time;

    public SingeSpeedResult(Double value, Date time) {
        this.value = value;
        this.time = time;
    }

    public Double getValue() {
        return value;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public String toString() {
        return
            "SingeSpeedResult{" +
                "value=" +
                value +
                ", time="
                + time +
                '}';
    }
}
