package com.sloniec.road;

import com.sloniec.road.framework.ModuleRunner;
import com.sloniec.road.shared.Params;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

public class App {

    public static void main(String[] args) throws IOException {
        System.out.println("Rozpoczeto prace programu.");
        Instant start = Instant.now();
        Params params = Params.getInstance();
        params.init(args);

        ModuleRunner runner = new ModuleRunner();
        runner.run();

        Instant end = Instant.now();
        System.out.println("Zakonczono prace programu w czasie: " + Duration.between(start, end));
        System.out.println("Nacisniecie przycisku 'ENTER' zamknie to okno.");
        System.in.read();
    }
}
