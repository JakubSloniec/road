package com.sloniec.road.shared.module.saver;

import static java.util.Arrays.asList;

import com.sloniec.road.shared.commons.TimeCommons;
import com.sloniec.road.shared.result.GateResult;
import java.util.ArrayList;
import java.util.List;

public class GateResultSaver extends AbstractSaver<GateResult> {

    @Override
    protected List<List<String>> resultToString(GateResult result) {

        List<String> resultString = new ArrayList<>();
        resultString.add(TimeCommons.dateToString(result.getDateBefore()));
        resultString.add(TimeCommons.dateToString(result.getDateAfter()));

        return asList(resultString);
    }

    @Override
    protected List<String> header() {
        List<String> headers = new ArrayList<>();
        headers.add("czas przed");
        headers.add("czas po");
        return headers;
    }
}
