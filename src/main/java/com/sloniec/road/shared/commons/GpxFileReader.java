package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.gpxparser.GPXParser;
import com.sloniec.road.shared.gpxparser.modal.GPX;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import org.xml.sax.SAXParseException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GpxFileReader {

    public List<String> listFolderFiles(String folder) {
        List<String> files = new ArrayList<>();
        try {
            files = Files.list(Paths.get(folder))
                    .filter(Files::isRegularFile)
                    .map(path -> path.toAbsolutePath().toString())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }


    public GPX readFile(String file) throws SAXParseException {
        GPXParser p = new GPXParser();
        FileInputStream in;
        GPX gpx = null;
        try {
            in = new FileInputStream(file);
            gpx = p.parseGPX(in);
        } catch (SAXParseException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gpx;
    }

    public List<Waypoint> getWaypoints(GPX gpx) {
        List<Waypoint> waypoints = gpx.getTracks().iterator().next().getTrackSegments().iterator().next().getWaypoints();
        return Objects.isNull(waypoints) ? new ArrayList<>() : waypoints;
    }

    public GPX getGPX(String file) {
        GPX gpx;
        try {
            gpx = readFile(file);
        } catch (SAXParseException e) {
            return null;
        }
        return gpx;
    }

    public List<Waypoint> getWaypoints(String file) {
        return getWaypoints(getGPX(file));
    }

    public String getComment(GPX gpx) {
        return gpx.getTracks().iterator().next().getComment();
    }

}
