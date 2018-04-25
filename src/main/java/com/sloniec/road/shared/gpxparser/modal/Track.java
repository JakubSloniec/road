
package com.sloniec.road.shared.gpxparser.modal;

import java.util.ArrayList;
import java.util.HashSet;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = "name")
public class Track extends Extension {

    private String name;
    private String comment;
    private String description;
    private String src;
    private HashSet<Link> links;
    private Integer number;
    private String type;
    private ArrayList<TrackSegment> trackSegments;

    public void addLink(Link link) {
        if (links == null) {
            links = new HashSet<>();
        }
        links.add(link);
    }

    public void addTrackSegment(TrackSegment trackSegment) {
        if (trackSegments == null) {
            trackSegments = new ArrayList<>();
        }
        trackSegments.add(trackSegment);
    }
}
