package com.sloniec.road.shared.result;

import com.sloniec.road.framework.interf.IResult;
import com.sloniec.road.shared.commons.Segment;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GateResult implements IResult {

    private Date dateBefore;
    private Date dateAfter;
    private String fileName;

    public GateResult(Segment segment) {
        dateBefore = segment.getP1().getTime();
        dateAfter = segment.getP2().getTime();
    }
}
