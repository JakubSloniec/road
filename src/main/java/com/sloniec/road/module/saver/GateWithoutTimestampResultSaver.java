package com.sloniec.road.module.saver;

import com.sloniec.road.module.result.GateWithoutTimestampResult;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class GateWithoutTimestampResultSaver extends AbstractSaver<GateWithoutTimestampResult> {

    @Override
    protected List<List<String>> resultToString(GateWithoutTimestampResult result) {

        List<String> resultString = new ArrayList<>();
        resultString.add(result.getStartDate());
        resultString.add(result.getFileName());
        return asList(resultString);
    }

    @Override
    protected List<String> header() {
        List<String> headers = new ArrayList<>();
        headers.add("czas_poczatku_trasy");
        headers.add("plik");
        return headers;
    }
}
