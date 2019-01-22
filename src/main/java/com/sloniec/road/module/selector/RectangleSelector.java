package com.sloniec.road.module.selector;

import static com.sloniec.road.shared.commons.SegmentCommons.waypointsToSegments;

import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.Area;
import com.sloniec.road.shared.commons.AreaCommons;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;
import java.util.function.Predicate;

public class RectangleSelector extends AbstractSelector {
    public RectangleSelector(GpxFileReader fileReader) {
        super(fileReader);
    }

    @Override
    protected Predicate<String> getFilters() {
        return file -> {
            List<Waypoint> waypoints = fileReader.getWaypoints(file);
            Area a = Context.getRectangle();

            return waypointsToSegments(waypoints)
                .anyMatch(s -> AreaCommons.isSegmentIntersectingWithArea(a, s));
        };
    }
}
