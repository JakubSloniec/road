package com.sloniec.road.shared.gpxparser;

import com.sloniec.road.shared.Params;
import com.sloniec.road.shared.gpxparser.extension.IExtensionParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

class BaseGPX {

    final SimpleDateFormat xmlDateFormat = Params.getXmlDateFormat();
    final ArrayList<IExtensionParser> extensionParsers = new ArrayList<>();

    BaseGPX() {
        // TF, 20170515: iso6801 dates are always in GMT timezone
        xmlDateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Warsaw"));
    }

    /**
     * Adds a new extension parser to be used when parsing a gpx steam
     *
     * @param parser an instance of a {@link IExtensionParser} implementation
     */
    public void addExtensionParser(IExtensionParser parser) {
        extensionParsers.add(parser);
    }

    /**
     * Removes an extension parser previously added
     *
     * @param parser an instance of a {@link IExtensionParser} implementation
     */
    public void removeExtensionParser(IExtensionParser parser) {
        extensionParsers.remove(parser);
    }
}
