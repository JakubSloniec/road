package com.sloniec.road.framework;

import java.util.List;

public interface ISaver {
    void save(List<? extends IResult> results);

    List<List<String>> resultsToString(List<? extends IResult> results);

    List<String> header();

    String outputFile();
}
