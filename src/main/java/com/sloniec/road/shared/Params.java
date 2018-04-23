package com.sloniec.road.shared;

import com.sloniec.road.framework.ModuleType;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Properties;

import static java.nio.file.Files.newBufferedReader;

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
    private static Boolean filterSwitch;
    private static Double filterValue;

    public static Params getInstance() {
        if (instance == null) {
            instance = new Params();
        }
        return instance;
    }

    private Params() {
        try {
            load(newBufferedReader(Paths.get("config.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() {
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

        filterSwitch = Boolean.valueOf(getProperty("filter_swich"));

        try {
            filterValue = Double.valueOf(getProperty("filter_value"));
        } catch (NullPointerException | NumberFormatException ex) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'filter_value'");
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

    public static Boolean getFilterSwitch() {
        return filterSwitch;
    }

    public static Double getFilterValue() {
        return filterValue;
    }
}
