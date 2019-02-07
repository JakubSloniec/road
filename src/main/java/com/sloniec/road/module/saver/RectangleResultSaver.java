package com.sloniec.road.module.saver;

import com.sloniec.road.module.result.RectangleResult;
import com.sloniec.road.module.result.SingleRectangleResult;
import com.sloniec.road.shared.commons.TimeCommons;
import java.util.ArrayList;
import java.util.List;

public class RectangleResultSaver extends AbstractSaver<RectangleResult> {

    @Override
    protected List<List<String>> resultToString(RectangleResult result) {

        return new ArrayList<>(speedResultsToString(
            getPreColumns(result),
            result.getSpeeds()
        ));
    }

    private List<String> getPreColumns(RectangleResult result) {
        List<String> preColumns = new ArrayList<>();
        preColumns.addAll(waypointToString(result.getBeginningWaypoint()));
        preColumns.add(result.getFile());
        preColumns.add(Double.toString(result.getStep()));
        return preColumns;
    }

    private List<List<String>> speedResultsToString(List<String> preColumns, List<SingleRectangleResult> speedResults) {
        List<List<String>> all = new ArrayList<>();
        for (SingleRectangleResult speedResult : speedResults) {
            List<String> single = new ArrayList<>();
            single.addAll(preColumns);
            single.add(TimeCommons.dateToString(speedResult.getTime()));
            single.add(Double.toString(speedResult.getValue()));
            single.add(Double.toString(speedResult.getAcceleration()));
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
        headers.add("czas");
        headers.add("predkosc");
        headers.add("przyszpieszenie");
        return headers;
    }
}
