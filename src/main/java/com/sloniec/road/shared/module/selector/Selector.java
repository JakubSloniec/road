package com.sloniec.road.shared.module.selector;

import static com.sloniec.road.shared.Context.getFilterSwitchTimeDistance;
import static com.sloniec.road.shared.Context.getFilterValueTimeDistance;
import static com.sloniec.road.shared.commons.TimeCommons.seconds;

import com.sloniec.road.framework.ISelector;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.xml.sax.SAXParseException;

public abstract class Selector implements ISelector {

    protected GpxFileReader fileReader;

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
            .filter(getFilters())
            .collect(Collectors.toList());

        System.out.println("Liczba wybranych danych: " + selectedFiles.size());
        return selectedFiles;
    }

    protected abstract Predicate<String> getFilters();

    protected Predicate<String> isFileSuccessfullyParsed() {
        return file -> {
            try {
                fileReader.readFile(file);
            } catch (SAXParseException e) {
                System.out.println("BLAD WCZYTYWANIA PLIKU: " + file);
                return false;
            }
            return true;
        };
    }

    protected Predicate<String> isTotalTimeDistanceNotTooGreat() {
        return file -> {
            if (getFilterSwitchTimeDistance()) {
                List<Waypoint> waypoints = fileReader.getWaypoints(file);
                Double maxTimeDistance = getFilterValueTimeDistance();

                if (seconds(waypoints.get(0), waypoints.get(waypoints.size() - 1)) > maxTimeDistance) {
                    System.out.println("Trasa powyzej " + maxTimeDistance + "s w pliku " + file);
                    return false;
                }
            }
            return true;
        };
    }
}
