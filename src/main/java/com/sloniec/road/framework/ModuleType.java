package com.sloniec.road.framework;

import com.sloniec.road.module.stravafolder.StravaFileReader;
import com.sloniec.road.module.wavelo.WaveloFileReader;
import com.sloniec.road.module.wavelo.wavelofile.WaveloFilePreparator;
import com.sloniec.road.shared.module.FolderPreparator;
import com.sloniec.road.shared.module.Processor;
import com.sloniec.road.shared.module.Saver;
import com.sloniec.road.shared.module.Selector;

public enum ModuleType {
    WAVELO_PLIK(new WaveloFilePreparator(), new Selector(new WaveloFileReader()), new Processor(new WaveloFileReader()), new Saver()),
    WAVELO_FOLDER(new FolderPreparator(), new Selector(new WaveloFileReader()), new Processor(new WaveloFileReader()), new Saver()),
    STRAVA_FOLDER(new FolderPreparator(), new Selector(new StravaFileReader()), new Processor(new StravaFileReader()), new Saver());

    private final IPreparator preparator;
    private final ISelector selector;
    private final IProcessor processor;
    private final ISaver saver;

    ModuleType(IPreparator preparator, ISelector selector, IProcessor processor, ISaver saver) {
        this.preparator = preparator;
        this.selector = selector;
        this.processor = processor;
        this.saver = saver;
    }

    public IPreparator getPreparator() {
        return preparator;
    }

    public ISelector getSelector() {
        return selector;
    }

    public IProcessor getProcessor() {
        return processor;
    }

    public ISaver getSaver() {
        return saver;
    }
    }
