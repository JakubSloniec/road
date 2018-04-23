package com.sloniec.road.framework;

import java.util.List;

public interface IProcessor {
    List<? extends IResult> process(List<String> files);
}
