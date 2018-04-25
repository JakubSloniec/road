package com.sloniec.road.shared.gpxparser.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Bounds {

    private double minLat;
    private double maxLat;
    private double minLon;
    private double maxLon;
}
