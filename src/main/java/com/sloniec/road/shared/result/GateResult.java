package com.sloniec.road.shared.result;

import com.sloniec.road.framework.IResult;
import com.sloniec.road.shared.commons.Segment;
import java.util.Date;
import lombok.Getter;

@Getter
public class GateResult implements IResult {

    private Date dateBefore;
    private Date dateAfter;

    public GateResult(Segment segment) {
        dateBefore = segment.getP1().getTime();
        dateAfter = segment.getP2().getTime();
    }
}
