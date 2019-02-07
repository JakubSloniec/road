package com.sloniec.road.module.result;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SingleRectangleResult {
    private Double value;
    private Date time;
    private Double acceleration;

    public SingleRectangleResult(SingleSpeedResult singleSpeedResult) {
        this.value = singleSpeedResult.getValue();
        this.time = singleSpeedResult.getTime();
        this.acceleration = 0d;
    }
}
