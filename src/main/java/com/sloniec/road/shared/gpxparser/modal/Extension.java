
package com.sloniec.road.shared.gpxparser.modal;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

/**
 * This class holds generic extension information from any node that can have extensions defined.
 * <br>
 * <p>Any custom extension parser used when parsing a gpx file will return a custom object
 * instance that will be kept in properties defined in this class.</p>
 * <br>
 * <p>Multiple extension parsers can be used when parsing. Every extension parser defines
 * an unique id that will be used as a HashMap key for the parsed object values.</p>
 */
@Getter
@Setter
public class Extension {

    protected HashMap<String, Object> extensionData;

    /**
     * Adds a new parsed extension object into the extension data with the key set by parserId.
     *
     * @param parserId a unique key representing the id of he extension parser used.
     * @param data an object holding the parsed information. This can be any object type and it is the extension parser's job to set it properly.
     */
    public void addExtensionData(String parserId, Object data) {
        if (extensionData == null) {
            extensionData = new HashMap<>();
        }
        extensionData.put(parserId, data);
    }

    /**
     * Returns the extension data parsed by the extension parser with id <i>parserId</i>
     *
     * @param parserId a String representing the id of an extension parser
     * @return the extension data parsed by the extension parser with id <i>parserId</i>
     */
    public Object getExtensionData(String parserId) {
        if (extensionData != null) {
            return extensionData.get(parserId);
        } else {
            return null;
        }
    }

    /**
     * Returns the number of extension data objects that are currently set.
     *
     * @return the number of extension data objects that are currently set.
     */
    public int getExtensionsParsed() {
        if (extensionData != null) {
            return extensionData.size();
        } else {
            return 0;
        }
    }
}
