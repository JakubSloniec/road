package com.sloniec.road.module.saver;

import static java.util.Arrays.asList;

import com.sloniec.road.module.result.GateResult;
import com.sloniec.road.shared.commons.TimeCommons;
import java.util.ArrayList;
import java.util.List;

public class GateResultSaver extends AbstractSaver<GateResult> {

    @Override
    protected List<List<String>> resultToString(GateResult result) {

        List<String> resultString = new ArrayList<>();
        resultString.add(TimeCommons.dateToString(result.getDateBefore()));
        resultString.add(TimeCommons.dateToString(result.getDateAfter()));
        resultString.add(result.getFileName());
        return asList(resultString);
    }

    @Override
    protected List<String> header() {
        List<String> headers = new ArrayList<>();
        headers.add("czas_przed");
        headers.add("czas_po");
        headers.add("plik");
        return headers;
    }
}
