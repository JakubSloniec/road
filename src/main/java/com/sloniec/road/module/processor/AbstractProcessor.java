package com.sloniec.road.module.processor;

import static java.util.stream.Collectors.toList;

import com.sloniec.road.framework.interf.IProcessor;
import com.sloniec.road.framework.interf.IResult;
import com.sloniec.road.shared.commons.GpxFileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractProcessor<T extends IResult> implements IProcessor {

    protected GpxFileReader reader;
    protected int incorrectResults = 0;

    public AbstractProcessor(GpxFileReader reader) {
        this.reader = reader;
    }

    @Override
    public List<T> process(List<String> files) {
        System.out.println("Liczba danych do procesowania: " + files.size());

        List<T> results = files.stream()
            .map(this::processFile)
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .filter(Objects::nonNull)
            .collect(toList());

        if (shouldVerifyResult()) {
            results = verifyResults(results);
            System.out.println("Liczba odrzucownych wynikow: " + incorrectResults);
        }
        return results;
    }

    protected List<T> verifyResults(List<T> results) {
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

    abstract List<T> processFile(String file);

    abstract boolean shouldVerifyResult();

    abstract boolean verifyResult(T result);
}
