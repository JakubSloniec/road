package com.sloniec.road;

import com.sloniec.road.framework.ModuleRunner;
import com.sloniec.road.shared.Context;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException {
        log.info("Rozpoczeto prace programu");
        Instant start = Instant.now();
        Context context = Context.getInstance();
        context.init(args);

        ModuleRunner runner = new ModuleRunner();
        runner.run();

        Instant end = Instant.now();
        log.info("");
        log.info("Zakonczono prace programu w czasie: [{}]", Duration.between(start, end));
        log.info("Nacisniecie przycisku 'ENTER' zamknie to okno.");
        System.in.read();
    }
}
