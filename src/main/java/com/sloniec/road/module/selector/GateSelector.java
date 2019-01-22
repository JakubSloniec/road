package com.sloniec.road.module.selector;

import com.sloniec.road.shared.commons.GpxFileReader;
import java.util.function.Predicate;

public class GateSelector extends AbstractSelector {
    public GateSelector(GpxFileReader fileReader) {
        super(fileReader);
    }

    @Override
    protected Predicate<String> getFilters() {
        return file -> true;
    }
}
