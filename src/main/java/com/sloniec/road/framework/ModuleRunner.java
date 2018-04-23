package com.sloniec.road.framework;

import com.sloniec.road.shared.Params;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class ModuleRunner {

    private IPreparator preparator;
    private ISelector selector;
    private IProcessor processor;
    private ISaver saver;

    public ModuleRunner() {
        ModuleType moduleType = Params.getModuleType();
        System.out.println("Wybrany modul: " + moduleType.name());

        preparator = moduleType.getPreparator();
        selector = moduleType.getSelector();
        processor = moduleType.getProcessor();
        saver = moduleType.getSaver();
    }

    public void run() {
        Instant start;

        start = begin("przygotowanie danych");
        String prepareOutput = preparator.prepare(Params.getDataLocation());
        end("przygowanie danych", start);

        start = begin("wybieranie danych");
        List<String> selectOutput = selector.select(prepareOutput);
        end("wybieranie danych", start);

        start = begin("procesowanie danych...");
        List<? extends IResult> processOutput = processor.process(selectOutput);
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
