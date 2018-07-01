package com.sloniec.road.shared.module.selector;

import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;
import java.util.function.Predicate;

public class SpeedSelector extends AbstractSelector {

    private PointInAreaCommons pointInAreaChecker = new PointInAreaCommons();

    public SpeedSelector(GpxFileReader fileReader) {
        super(fileReader);
    }

    @Override
    protected Predicate<String> getFilters() {
        return isFileSuccessfullyParsed()
            .and(isTotalTimeDistanceNotTooGreat())
            .and(hasFilePointInPolygons());
    }

    private Predicate<String> hasFilePointInPolygons() {
        return file -> {
            List<Waypoint> waypoints = fileReader.getWaypoints(file);
            Waypoint przed = pointInAreaChecker.getFirstPointInArea(Context.getBeforeArea(), waypoints);
            if (przed == null) {
                return false;
            }
            Waypoint wTrakcie = pointInAreaChecker.getFirstPointInArea(Context.getDuringArea(), waypoints.subList(waypoints.indexOf(przed), waypoints.size()));
            if (wTrakcie == null) {
                return false;
            }
            Waypoint po = pointInAreaChecker.getFirstPointInArea(Context.getAfterArea(), waypoints.subList(waypoints.indexOf(wTrakcie), waypoints.size()));
            if (po == null) {
                return false;
            }
            return true;
        };
    }
}
