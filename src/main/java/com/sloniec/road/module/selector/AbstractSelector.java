package com.sloniec.road.module.selector;

import com.sloniec.road.framework.interf.ISelector;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.commons.GpxFileReader;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import lombok.extern.slf4j.Slf4j;
import org.xml.sax.SAXParseException;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.sloniec.road.framework.config.ProcessingType.PRZEPUST_BEZ_CZASU;
import static com.sloniec.road.shared.Context.getFilterSwitchTimeDistance;
import static com.sloniec.road.shared.Context.getFilterValueTimeDistance;
import static com.sloniec.road.shared.commons.TimeCommons.seconds;

@Slf4j
public abstract class AbstractSelector implements ISelector {

    protected GpxFileReader fileReader;

    public AbstractSelector(GpxFileReader fileReader) {
        this.fileReader = fileReader;
    }

    @Override
    public List<String> select(String folder) {
        return selectFiles(folder);
    }

    private List<String> selectFiles(String folder) {
        List<String> files = fileReader.listFolderFiles(folder);
        log.info("Liczba wszystkich danych: [{}]", files.size());

        List<String> selectedFiles = files.stream()
                .filter(getBaseFilters()
                        .and(getFilters()))
                .collect(Collectors.toList());

        log.info("Liczba wybranych danych: [{}]", selectedFiles.size());
        return selectedFiles;
    }

    protected abstract Predicate<String> getFilters();

    private Predicate<String> getBaseFilters() {
        return isFileSuccessfullyParsed()
                .and(isTotalTimeDistanceNotTooGreat())
                .and(fileContainsWaypoints());
    }

    private Predicate<String> isFileSuccessfullyParsed() {
        return file -> {
            try {
                fileReader.readFile(file);
            } catch (SAXParseException e) {
                log.info("Blad wczytywania pliku: [{}]", file);
                return false;
            }
            return true;
        };
    }

    private Predicate<String> isTotalTimeDistanceNotTooGreat() {
        return file -> {
            if (getFilterSwitchTimeDistance() && !PRZEPUST_BEZ_CZASU.equals(Context.getProcessingType())) { // HACK to avoid filter activation when there is no timestamps
                List<Waypoint> waypoints = fileReader.getWaypoints(file);
                Double maxTimeDistance = getFilterValueTimeDistance();

                if (seconds(waypoints.get(0), waypoints.get(waypoints.size() - 1)) > maxTimeDistance) {
                    log.info("Trasa powyzej [{}]s w pliku [{}]", maxTimeDistance, file);
                    return false;
                }
            }
            return true;
        };
    }

    private Predicate<String> fileContainsWaypoints() {
        return file -> {
            if (fileReader.getWaypoints(file).isEmpty()) {
                log.error("Plik [{}] nie zawiera zadnych punktow", file);
                return false;
            }
            return true;
        };
    }
}
