package com.sloniec.road.framework.config;

import static com.sloniec.road.framework.config.DataSource.STRAVA_FOLDER;
import static com.sloniec.road.framework.config.DataSource.WAVELO_FOLDER;
import static com.sloniec.road.framework.config.DataSource.WAVELO_PLIK;
import static com.sloniec.road.framework.config.ProcessingType.PREDKOSC;
import static com.sloniec.road.framework.config.ProcessingType.PRZEPUST;
import static java.util.Arrays.asList;

import com.sloniec.road.framework.interf.IPreparator;
import com.sloniec.road.framework.interf.IProcessor;
import com.sloniec.road.framework.interf.ISaver;
import com.sloniec.road.framework.interf.ISelector;
import com.sloniec.road.module.preparator.FolderPreparator;
import com.sloniec.road.module.preparator.WaveloFilePreparator;
import com.sloniec.road.module.processor.GateProcessor;
import com.sloniec.road.module.processor.SpeedProcessor;
import com.sloniec.road.module.reader.StravaFileReader;
import com.sloniec.road.module.reader.WaveloFileReader;
import com.sloniec.road.module.saver.GateResultSaver;
import com.sloniec.road.module.saver.SpeedResultSaver;
import com.sloniec.road.module.selector.GateSelector;
import com.sloniec.road.module.selector.SpeedSelector;
import com.sloniec.road.shared.commons.GpxFileReader;
import java.util.List;
import lombok.Getter;

@Getter
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
        }
        return null;
    }

    private static IProcessor resolveProcessor(DataSource dataSource, ProcessingType processingType) {
        if (PREDKOSC.equals(processingType)) {
            return new SpeedProcessor(resolveFileReader(dataSource));
        } else if (PRZEPUST.equals(processingType)) {
            return new GateProcessor(resolveFileReader(dataSource));
        }
        return null;
    }

    private static ISaver resolveSaver(ProcessingType processingType) {
        if (PREDKOSC.equals(processingType)) {
            return new SpeedResultSaver();
        } else if (PRZEPUST.equals(processingType)) {
            return new GateResultSaver();
        }
        return null;
    }

    private static GpxFileReader resolveFileReader(DataSource dataSource) {
        if (STRAVA_DS.contains(dataSource)) {
            return new StravaFileReader();
        } else if (WAVELO_DS.contains(dataSource)) {
            return new WaveloFileReader();
        }
        return null;
    }
}
