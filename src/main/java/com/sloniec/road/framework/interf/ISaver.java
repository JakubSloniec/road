package com.sloniec.road.framework.interf;

import java.util.List;

public interface ISaver<T extends IResult> {
    void save(List<T> results);

    List<List<String>> resultsToString(List<T> results);

    String outputFile();
}
