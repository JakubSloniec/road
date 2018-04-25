package com.sloniec.road.shared.result;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString()
public class SingeSpeedResult {

    private Double value;
    private Date time;
}
