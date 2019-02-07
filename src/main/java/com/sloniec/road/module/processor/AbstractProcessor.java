package com.sloniec.road.module.processor;

import static java.util.stream.Collectors.toList;

import com.sloniec.road.framework.interf.IProcessor;
import com.sloniec.road.framework.interf.IResult;
import com.sloniec.road.shared.commons.FileCommons;
import com.sloniec.road.shared.commons.GpxFileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractProcessor<T extends IResult> implements IProcessor {

    protected GpxFileReader reader;
    private int incorrectResults = 0;

    public AbstractProcessor(GpxFileReader reader) {
        this.reader = reader;
    }

    @Override
    public List<T> process(List<String> files) {
        log.info("Liczba danych do procesowania: [{}]", files.size());

        List<T> results = files.stream()
            .map(this::verifyAndProcess)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .filter(Objects::nonNull)
            .collect(toList());

        if (shouldVerifyResult()) {
            results = verifyResults(results);
            log.info("Liczba odrzucownych wynikow: [{}]", incorrectResults);
        }
        return results;
    }

    abstract List<T> processFile(String file);

    abstract boolean shouldVerifyResult();

    abstract boolean verifyResult(T result);

    private List<T> verifyResults(List<T> results) {
        if (shouldVerifyResult()) {
            List<T> verifiedResults = new ArrayList<>();
            for (T result : results) {
                if (verifyResult(result)) {
                    verifiedResults.add(result);
                } else {
                    incorrectResults++;
                }
            }
            return verifiedResults;
        }
        return results;
    }

    private List<T> verifyAndProcess(String file) {
        FileCommons.checkIfIsFile(file);
        return processFile(file);
    }
}
