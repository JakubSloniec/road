package com.sloniec.road.framework;

import static com.sloniec.road.shared.Context.getDataLocation;
import static com.sloniec.road.shared.Context.getDataSource;
import static com.sloniec.road.shared.Context.getProcessingType;

import com.sloniec.road.framework.config.RunSetup;
import com.sloniec.road.shared.Context;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class ModuleRunner {

    private IPreparator preparator;
    private ISelector selector;
    private IProcessor processor;
    private ISaver saver;

    public ModuleRunner() {
        RunSetup runSetup = Context.getRunSetup();
        System.out.println("Wybrane opcje: " + getDataSource() + ", " + getProcessingType());

        preparator = runSetup.getPreparator();
        selector = runSetup.getSelector();
        processor = runSetup.getProcessor();
        saver = runSetup.getSaver();
    }

    public void run() {
        Instant start;

        start = begin("przygotowanie danych");
        String prepareOutput = preparator.prepare(getDataLocation());
        end("przygowanie danych", start);

        start = begin("wybieranie danych");
        List<String> selectOutput = selector.select(prepareOutput);
        end("wybieranie danych", start);

        start = begin("procesowanie danych");
        List<? extends IResult> processOutput = processor.process(selectOutput);
        System.out.println("Liczba wygenerowanych wynikow: " + processOutput.size());
        end("procesowanie danych", start);

        start = begin("zapisywanie wynikow");
        saver.save(processOutput);
        end("zapisywanie wynikow", start);
    }

    private Instant begin(String part) {
        System.out.println();
        System.out.println("Rozpoczeto " + part + "...");
        return Instant.now();
    }

    private void end(String part, Instant start) {
        Instant end = Instant.now();
        System.out.println("Zakonczono " + part + " w czasie: " + Duration.between(start, end));
    }
}
