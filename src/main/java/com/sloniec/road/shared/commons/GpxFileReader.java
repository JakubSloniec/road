package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.gpxparser.GPXParser;
import com.sloniec.road.shared.gpxparser.modal.GPX;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class GpxFileReader {

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

    public GPX readFile(String file) {
        GPXParser p = new GPXParser();
        FileInputStream in;
        GPX gpx = null;
        try {
            in = new FileInputStream(file);
            gpx = p.parseGPX(in);
        } catch (Exception e) {
            System.out.println("BLAD WCZYTYWANIA PLIKU: " + file + " ------------------");
//            e.printStackTrace();
        }
        return gpx;
    }

    public abstract List<Waypoint> getWaypoints(GPX gpx);

    public List<Waypoint> getWaypoints(String file) {
        return getWaypoints(readFile(file));
    }
}
