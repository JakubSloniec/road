package com.sloniec.road.framework;

import static com.sloniec.road.shared.Context.getDataLocation;
import static com.sloniec.road.shared.Context.getDataSource;
import static com.sloniec.road.shared.Context.getProcessingType;
import static com.sloniec.road.shared.commons.TimeCommons.formatTime;

import com.sloniec.road.framework.config.RunSetup;
import com.sloniec.road.framework.interf.IPreparator;
import com.sloniec.road.framework.interf.IProcessor;
import com.sloniec.road.framework.interf.IResult;
import com.sloniec.road.framework.interf.ISaver;
import com.sloniec.road.framework.interf.ISelector;
import com.sloniec.road.shared.Context;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModuleRunner {

    private IPreparator preparator;
    private ISelector selector;
    private IProcessor processor;
    private ISaver saver;

    public ModuleRunner() {
        RunSetup runSetup = Context.getRunSetup();
        log.info("Wybrane opcje: [{}], [{}]", getDataSource(), getProcessingType());

        preparator = runSetup.getPreparator();
        selector = runSetup.getSelector();
        processor = runSetup.getProcessor();
        saver = runSetup.getSaver();
    }

    public void run() {
        LocalDateTime start;

        start = begin("przygotowanie danych");
        String prepareOutput = preparator.prepare(getDataLocation());
        end("przygowanie danych", start);

        start = begin("wybieranie danych");
        List<String> selectOutput = selector.select(prepareOutput);
        end("wybieranie danych", start);

        start = begin("procesowanie danych");
        List<? extends IResult> processOutput = processor.process(selectOutput);
        log.info("Liczba wygenerowanych wynikow: [{}]", processOutput.size());
        end("procesowanie danych", start);

        start = begin("zapisywanie wynikow");
        saver.save(processOutput);
        end("zapisywanie wynikow", start);
    }

    private LocalDateTime begin(String part) {
        log.info("");
        log.info("Rozpoczeto [{}]...", part);
        return LocalDateTime.now();
    }

    private void end(String part, LocalDateTime start) {
        LocalDateTime end = LocalDateTime.now();
        log.info("Zakonczono [{}] w czasie: [{}]", part, formatTime(start, end));
    }
}
