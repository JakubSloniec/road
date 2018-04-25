package com.sloniec.road.shared.gpxparser.modal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Link {

    private String href;

    private String text;
    private String type;

    public Link(String href) {
        this.href = href;
    }
}
