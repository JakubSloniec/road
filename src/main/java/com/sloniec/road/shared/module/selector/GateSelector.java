package com.sloniec.road.shared.module.selector;

import com.sloniec.road.shared.commons.GpxFileReader;
import java.util.function.Predicate;

public class GateSelector extends Selector {
    public GateSelector(GpxFileReader fileReader) {
        super(fileReader);
    }

    @Override
    protected Predicate<String> getFilters() {
        return isFileSuccessfullyParsed()
            .and(isTotalTimeDistanceNotTooGreat());
    }
}
