package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Segment {
    private Waypoint p1;
    private Waypoint p2;
}
