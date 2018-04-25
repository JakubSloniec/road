package com.sloniec.road.shared;

import static java.nio.file.Files.newBufferedReader;

import com.sloniec.road.framework.ModuleType;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Properties;

public class Params extends Properties {

    private static Params instance = null;

    private static SimpleDateFormat xmlDateFormat;
    private static ModuleType moduleType;
    private static String dataLocation;
    private static Area beforeArea;
    private static Area duringArea;
    private static Area afterArea;
    private static Double step;
    private static String rootPath;
    private static Boolean filterSwitchRecordsPerRegion;
    private static Double filterValueRecordsPerRegion;
    private static Boolean filterSwitchTimeDistance;
    private static Double filterValueTimeDistance;

    public static Params getInstance() {
        if (instance == null) {
            instance = new Params();
        }
        return instance;
    }

    private Params() {
    }

    public void init(String[] args) {
        if (args.length != 1) {
            System.out.println("Nieprawne wywolanie programu!");
            System.exit(0);
        }

        try {
            load(newBufferedReader(Paths.get(args[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            moduleType = ModuleType.valueOf(getProperty("modul"));
        } catch (IllegalArgumentException e) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'modul'");
            System.exit(0);
        }

        dataLocation = getProperty("dane");
        beforeArea = new Area(getProperty("przed_a"), getProperty("przed_b"), getProperty("przed_c"), getProperty("przed_d"));
        duringArea = new Area(getProperty("w_trakcie_a"), getProperty("w_trakcie_b"), getProperty("w_trakcie_c"), getProperty("w_trakcie_d"));
        afterArea = new Area(getProperty("po_a"), getProperty("po_b"), getProperty("po_c"), getProperty("po_d"));

        try {
            step = Double.valueOf(getProperty("krok"));
        } catch (NullPointerException | NumberFormatException ex) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'krok'");
            System.exit(0);
        }

        rootPath = Paths.get("").toAbsolutePath().toString();

        if (ModuleType.STRAVA_FOLDER.equals(moduleType)) {
            xmlDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        } else {
            xmlDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        }

        filterSwitchRecordsPerRegion = Boolean.valueOf(getProperty("filter_switch_records_per_region"));

        try {
            filterValueRecordsPerRegion = Double.valueOf(getProperty("filter_value_records_per_region"));
        } catch (NullPointerException | NumberFormatException ex) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'filter_value_records_per_region'");
            System.exit(0);
        }

        filterSwitchTimeDistance = Boolean.valueOf(getProperty("filter_switch_time_distance"));

        try {
            filterValueTimeDistance = Double.valueOf(getProperty("filter_value_time_distance"));
        } catch (NullPointerException | NumberFormatException ex) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'filter_value_time_distance'");
            System.exit(0);
        }
    }


    public static ModuleType getModuleType() {
        return moduleType;
    }

    public static String getDataLocation() {
        return dataLocation;
    }

    public static Area getBeforeArea() {
        return beforeArea;
    }

    public static Area getDuringArea() {
        return duringArea;
    }

    public static Area getAfterArea() {
        return afterArea;
    }

    public static Double getStep() {
        return step;
    }

    public static String getRootPath() {
        return rootPath;
    }

    public static SimpleDateFormat getXmlDateFormat() {
        return xmlDateFormat;
    }

    public static Boolean getFilterSwitchRecordsPerRegion() {
        return filterSwitchRecordsPerRegion;
    }

    public static Double getFilterValueRecordsPerRegion() {
        return filterValueRecordsPerRegion;
    }

    public static Boolean getFilterSwitchTimeDistance() {
        return filterSwitchTimeDistance;
    }

    public static Double getFilterValueTimeDistance() {
        return filterValueTimeDistance;
    }
}
