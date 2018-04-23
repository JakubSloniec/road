package com.sloniec.road.shared.module;

import com.sloniec.road.framework.ISelector;
import com.sloniec.road.shared.Params;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;

import java.util.List;
import java.util.stream.Collectors;

public class Selector implements ISelector {

    private GpxFileReader fileReader;
    private PointInAreaCommons pointInAreaChecker = new PointInAreaCommons();

    public Selector(GpxFileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public List<String> select(String folder) {
        List<String> selectedFiles = selectFiles(folder);
        return selectedFiles;
    }


    public List<String> selectFiles(String folder) {
        List<String> files = fileReader.listFolderFiles(folder);
        System.out.println("Liczba orginalnych danych: " + files.size());

        List<String> selectedFiles = files.stream()
            .filter(this::hasFilePointInPolygons)
            .collect(Collectors.toList());

        System.out.println("Liczba wybranych danych: " + selectedFiles.size());
        return selectedFiles;
    }

    public boolean hasFilePointInPolygons(String file) {
        List<Waypoint> waypoints = fileReader.getWaypoints(file);
        Waypoint przed = pointInAreaChecker.getFirstPointInArea(Params.getBeforeArea(), waypoints);
        if (przed == null) {
            return false;
        }
        Waypoint wTrakcie = pointInAreaChecker.getFirstPointInArea(Params.getDuringArea(), waypoints.subList(waypoints.indexOf(przed), waypoints.size()));
        if (wTrakcie == null) {
            return false;
        }
        Waypoint po = pointInAreaChecker.getFirstPointInArea(Params.getAfterArea(), waypoints.subList(waypoints.indexOf(wTrakcie), waypoints.size()));
        if (po == null) {
            return false;
        }
        return true;
    }
}
