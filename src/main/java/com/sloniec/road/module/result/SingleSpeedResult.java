package com.sloniec.road.module.result;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class SingleSpeedResult {
    private Double value;
    private Date time;
}
