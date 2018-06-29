package com.sloniec.road.shared.module.processor;

import com.sloniec.road.framework.IProcessor;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.result.GateResult;
import java.util.List;

public class GateProcessor implements IProcessor {

    private GpxFileReader reader;
    private PointInAreaCommons checker = new PointInAreaCommons();

    public GateProcessor(GpxFileReader reader) {
        this.reader = reader;
    }

    @Override
    public List<GateResult> process(List<String> files) {
        return null;
    }
}
