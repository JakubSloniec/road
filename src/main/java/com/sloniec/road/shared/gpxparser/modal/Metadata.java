package com.sloniec.road.shared.gpxparser.modal;

import java.util.Date;
import java.util.HashSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Metadata extends Extension {

    private String name;
    private String desc;
    private Person author;
    private Copyright copyright;
    private HashSet<Link> links;
    private Date time;
    private String keywords;
    private Bounds bounds;

    public void addLink(Link link) {
        if (links == null) {
            links = new HashSet<>();
        }
        links.add(link);
    }
}
