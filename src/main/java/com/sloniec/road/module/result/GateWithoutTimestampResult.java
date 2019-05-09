package com.sloniec.road.module.result;

import com.sloniec.road.framework.interf.IResult;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GateWithoutTimestampResult implements IResult {

    private String startDate;
    private String fileName;

    public GateWithoutTimestampResult(String startDate, String fileName) {
        this.startDate = startDate;
        this.fileName = fileName;
    }
}
