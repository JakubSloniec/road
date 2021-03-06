package com.sloniec.road.framework.config;

import com.sloniec.road.framework.interf.IPreparator;
import com.sloniec.road.framework.interf.IProcessor;
import com.sloniec.road.framework.interf.ISaver;
import com.sloniec.road.framework.interf.ISelector;
import com.sloniec.road.module.preparator.FolderPreparator;
import com.sloniec.road.module.preparator.WaveloFilePreparator;
import com.sloniec.road.module.processor.GateProcessor;
import com.sloniec.road.module.processor.GateWithoutTimestampProcessor;
import com.sloniec.road.module.processor.RectangleProcessor;
import com.sloniec.road.module.processor.SpeedProcessor;
import com.sloniec.road.module.saver.GateResultSaver;
import com.sloniec.road.module.saver.GateWithoutTimestampResultSaver;
import com.sloniec.road.module.saver.RectangleResultSaver;
import com.sloniec.road.module.saver.SpeedResultSaver;
import com.sloniec.road.module.selector.GateSelector;
import com.sloniec.road.module.selector.RectangleSelector;
import com.sloniec.road.module.selector.SpeedSelector;
import com.sloniec.road.shared.commons.GpxFileReader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static com.sloniec.road.framework.config.DataSource.*;
import static com.sloniec.road.framework.config.ProcessingType.*;
import static java.util.Arrays.asList;

@Getter
@Slf4j
public class RunSetup {

    private static final List<DataSource> STRAVA_DS = asList(STRAVA_FOLDER);
    private static final List<DataSource> WAVELO_DS = asList(WAVELO_PLIK, WAVELO_FOLDER);

    private IPreparator preparator;
    private ISelector selector;
    private IProcessor processor;
    private ISaver saver;

    public RunSetup(DataSource dataSource, ProcessingType processingType) {
        this(
                resolvePreparator(dataSource),
                resolveSelector(dataSource, processingType),
                resolveProcessor(dataSource, processingType),
                resolveSaver(processingType)
        );
    }

    private RunSetup(IPreparator preparator, ISelector selector, IProcessor processor, ISaver saver) {
        this.preparator = preparator;
        this.selector = selector;
        this.processor = processor;
        this.saver = saver;
    }

    private static IPreparator resolvePreparator(DataSource dataSource) {
        if (WAVELO_PLIK.equals(dataSource)) {
            return new WaveloFilePreparator();
        } else {
            return new FolderPreparator();
        }
    }

    private static ISelector resolveSelector(DataSource dataSource, ProcessingType processingType) {
        if (PREDKOSC.equals(processingType)) {
            return new SpeedSelector(resolveFileReader(dataSource));
        } else if (PRZEPUST.equals(processingType)) {
            return new GateSelector(resolveFileReader(dataSource));
        } else if (PRZEPUST_BEZ_CZASU.equals(processingType)) {
            return new GateSelector(resolveFileReader(dataSource));
        } else if (PROSTOKAT.equals(processingType)) {
            return new RectangleSelector(resolveFileReader(dataSource));
        }
        return null;
    }

    private static IProcessor resolveProcessor(DataSource dataSource, ProcessingType processingType) {
        if (PREDKOSC.equals(processingType)) {
            return new SpeedProcessor(resolveFileReader(dataSource));
        } else if (PRZEPUST.equals(processingType)) {
            return new GateProcessor(resolveFileReader(dataSource));
        } else if (PRZEPUST_BEZ_CZASU.equals(processingType)) {
            return new GateWithoutTimestampProcessor(resolveFileReader(dataSource));
        } else if (PROSTOKAT.equals(processingType)) {
            return new RectangleProcessor(resolveFileReader(dataSource));
        }
        return null;
    }

    private static ISaver resolveSaver(ProcessingType processingType) {
        if (PREDKOSC.equals(processingType)) {
            return new SpeedResultSaver();
        } else if (PRZEPUST.equals(processingType)) {
            return new GateResultSaver();
        } else if (PRZEPUST_BEZ_CZASU.equals(processingType)) {
            return new GateWithoutTimestampResultSaver();
        } else if (PROSTOKAT.equals(processingType)) {
            return new RectangleResultSaver();
        }
        return null;
    }

    private static GpxFileReader resolveFileReader(DataSource dataSource) {
        return new GpxFileReader();
    }
}
