package com.sloniec.road;

import static com.sloniec.road.shared.commons.TimeCommons.formatTime;

import com.sloniec.road.framework.ModuleRunner;
import com.sloniec.road.shared.Context;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static void main(String[] args) throws IOException {
        log.info("Rozpoczeto prace programu");
        LocalDateTime start = LocalDateTime.now();

        Context context = Context.getInstance();
        context.init(args);

        ModuleRunner runner = new ModuleRunner();
        runner.run();

        LocalDateTime end = LocalDateTime.now();
        log.info("");
        log.info("Zakonczono prace programu w czasie: [{}]", formatTime(start, end));
        log.info("Nacisniecie przycisku 'ENTER' zamknie to okno.");
        System.in.read();
    }
}
