package com.sloniec.road.shared.module;

import static com.sloniec.road.shared.Params.getFilterSwitchTimeDistance;
import static com.sloniec.road.shared.Params.getFilterValueTimeDistance;
import static com.sloniec.road.shared.commons.TimeCommons.seconds;

import com.sloniec.road.framework.ISelector;
import com.sloniec.road.shared.Params;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.commons.PointInAreaCommons;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;
import java.util.stream.Collectors;
import org.xml.sax.SAXParseException;

public class Selector implements ISelector {

    private GpxFileReader fileReader;
    private PointInAreaCommons pointInAreaChecker = new PointInAreaCommons();

    public Selector(GpxFileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public List<String> select(String folder) {
        return selectFiles(folder);
    }


    private List<String> selectFiles(String folder) {
        List<String> files = fileReader.listFolderFiles(folder);
        System.out.println("Liczba orginalnych danych: " + files.size());

        List<String> selectedFiles = files.stream()
            .filter(this::isFileSuccessfullyParsed)
            .filter(this::isTotalTimeDistanceNotTooGreat)
            .filter(this::hasFilePointInPolygons)
            .collect(Collectors.toList());

        System.out.println("Liczba wybranych danych: " + selectedFiles.size());
        return selectedFiles;
    }

    private boolean isFileSuccessfullyParsed(String file) {
        try {
            fileReader.readFile(file);
        } catch (SAXParseException e) {
            System.out.println("BLAD WCZYTYWANIA PLIKU: " + file);
            return false;
        }
        return true;
    }

    private boolean isTotalTimeDistanceNotTooGreat(String file) {
        if (getFilterSwitchTimeDistance()) {
            List<Waypoint> waypoints = fileReader.getWaypoints(file);
            Double maxTimeDistance = getFilterValueTimeDistance();

            if (seconds(waypoints.get(0), waypoints.get(waypoints.size() - 1)) > maxTimeDistance) {
                System.out.println("Trasa powyzej " + maxTimeDistance + "s w pliku " + file);
                return false;
            }
        }
        return true;
    }

    private boolean hasFilePointInPolygons(String file) {
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
