package com.sloniec.road.module.saver;

import com.sloniec.road.shared.commons.TimeCommons;
import com.sloniec.road.shared.result.SingeSpeedResult;
import com.sloniec.road.shared.result.SpeedResult;
import java.util.ArrayList;
import java.util.List;

public class SpeedResultSaver extends AbstractSaver<SpeedResult> {

    @Override
    protected List<List<String>> resultToString(SpeedResult result) {
        List<List<String>> stringResult = new ArrayList<>();

        List<String> preColumns = getPreColumns(result);

        stringResult.addAll(speedResultsToString(preColumns, "PRZED", result.getBeforeSpeeds()));
        stringResult.addAll(speedResultsToString(preColumns, "W_TRAKCIE", result.getDuringSpeeds()));
        stringResult.addAll(speedResultsToString(preColumns, "PO", result.getAfterSpeeds()));

        return stringResult;
    }

    private List<String> getPreColumns(SpeedResult result) {
        List<String> preColumns = new ArrayList<>();
        preColumns.addAll(waypointToString(result.getBeginningWaypoint()));
        preColumns.add(result.getFile());
        preColumns.add(Double.toString(result.getStep()));
        return preColumns;
    }

    private List<List<String>> speedResultsToString(List<String> preColumns, String place, List<SingeSpeedResult> speedResults) {
        List<List<String>> all = new ArrayList<>();
        for (SingeSpeedResult speedResult : speedResults) {
            List<String> single = new ArrayList<>();
            single.addAll(preColumns);
            single.add(place);
            single.add(TimeCommons.dateToString(speedResult.getTime()));
            single.add(Double.toString(speedResult.getValue()));
            all.add(single);
        }
        return all;
    }

    @Override
    protected List<String> header() {
        List<String> headers = new ArrayList<>();
        headers.add("start czas");
        headers.add("start lat");
        headers.add("start lon");
        headers.add("plik");
        headers.add("krok");
        headers.add("miejsce");
        headers.add("czas");
        headers.add("predkosc");
        return headers;
    }
}
