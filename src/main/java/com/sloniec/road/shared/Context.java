package com.sloniec.road.shared;

import static com.sloniec.road.framework.config.DataSource.STRAVA_FOLDER;
import static com.sloniec.road.framework.config.ProcessingType.PREDKOSC;
import static com.sloniec.road.framework.config.ProcessingType.PRZEPUST;
import static com.sloniec.road.shared.commons.GPXCommons.stringToPoint;
import static java.nio.file.Files.newBufferedReader;

import com.sloniec.road.framework.config.DataSource;
import com.sloniec.road.framework.config.ProcessingType;
import com.sloniec.road.framework.config.RunSetup;
import com.sloniec.road.shared.commons.Segment;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Properties;
import lombok.Getter;

@Getter
public class Context extends Properties {

    private static Context instance = null;

    private static DataSource dataSource;
    private static ProcessingType processingType;
    private static RunSetup runSetup;

    private static SimpleDateFormat xmlDateFormat;

    private static String dataLocation;

    private static Area beforeArea;
    private static Area duringArea;
    private static Area afterArea;

    private static Segment gate;

    private static Double step;

    private static String rootPath;

    private static Boolean filterSwitchRecordsPerRegion;
    private static Double filterValueRecordsPerRegion;

    private static Boolean filterSwitchTimeDistance;
    private static Double filterValueTimeDistance;

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
        }
        return instance;
    }

    private Context() {
    }

    public void init(String[] args) {
        loadPropertyFile(args);

        setDataSource();
        setProcessingType();
        setRunSetup();

        dataLocation = getProperty("dane");

        if (PREDKOSC.equals(processingType)) {
            setSpeedConfig();
        } else if (PRZEPUST.equals(processingType)) {
            setGateConfig();
        }

        setTimeStep();
        setTimeFormat();
        setMaxRecordsPerRegion();
        setMaxTimeDistance();
    }

    private void loadPropertyFile(String[] args) {
        if (args.length != 1) {
            System.out.println("Niepoprawne wywolanie programu!");
            System.exit(0);
        }

        try {
            load(newBufferedReader(Paths.get(args[0])));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDataSource() {
        try {
            dataSource = DataSource.valueOf(getProperty("zrodlo_danych"));
        } catch (IllegalArgumentException e) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'zrodlo_danych' lub 'procesowanie'");
            System.exit(0);
        }
    }

    private void setProcessingType() {
        try {
            processingType = ProcessingType.valueOf(getProperty("procesowanie"));
        } catch (IllegalArgumentException e) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'zrodlo_danych' lub 'procesowanie'");
            System.exit(0);
        }
    }

    private void setRunSetup() {
        try {
            runSetup = new RunSetup(dataSource, processingType);
        } catch (IllegalArgumentException e) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'zrodlo_danych' lub 'procesowanie'");
            System.exit(0);
        }
    }

    private void setSpeedConfig() {
        beforeArea = new Area(getProperty("przed_a"), getProperty("przed_b"), getProperty("przed_c"), getProperty("przed_d"));
        duringArea = new Area(getProperty("w_trakcie_a"), getProperty("w_trakcie_b"), getProperty("w_trakcie_c"), getProperty("w_trakcie_d"));
        afterArea = new Area(getProperty("po_a"), getProperty("po_b"), getProperty("po_c"), getProperty("po_d"));
    }

    private void setGateConfig() {
        gate = new Segment(stringToPoint(getProperty("przepust_a")), stringToPoint(getProperty("przepust_b")));
    }

    private void setTimeStep() {
        try {
            step = Double.valueOf(getProperty("krok"));
        } catch (NullPointerException | NumberFormatException ex) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'krok'");
            System.exit(0);
        }
    }

    private void setTimeFormat() {
        rootPath = Paths.get("").toAbsolutePath().toString();

        if (STRAVA_FOLDER.equals(dataSource)) {
            xmlDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        } else {
            xmlDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        }
    }

    private void setMaxRecordsPerRegion() {
        filterSwitchRecordsPerRegion = Boolean.valueOf(getProperty("filter_switch_records_per_region"));

        try {
            filterValueRecordsPerRegion = Double.valueOf(getProperty("filter_value_records_per_region"));
        } catch (NullPointerException | NumberFormatException ex) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'filter_value_records_per_region'");
            System.exit(0);
        }
    }

    private void setMaxTimeDistance() {
        filterSwitchTimeDistance = Boolean.valueOf(getProperty("filter_switch_time_distance"));

        try {
            filterValueTimeDistance = Double.valueOf(getProperty("filter_value_time_distance"));
        } catch (NullPointerException | NumberFormatException ex) {
            System.out.println("BLAD: niepoprawna wartosc w parametrze 'filter_value_time_distance'");
            System.exit(0);
        }
    }

    public static RunSetup getRunSetup() {
        return runSetup;
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static ProcessingType getProcessingType() {
        return processingType;
    }

    public static SimpleDateFormat getXmlDateFormat() {
        return xmlDateFormat;
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

    public static Segment getGate() {
        return gate;
    }

    public static Double getStep() {
        return step;
    }

    public static String getRootPath() {
        return rootPath;
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
