package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeCommons {

    public static Double seconds(Waypoint from, Waypoint to) {
        return seconds(from.getTime(), to.getTime());
    }

    public static Double halfBetween(Waypoint from, Waypoint to) {
        return (from.getTime().getTime() + to.getTime().getTime()) / 2d;
    }

    public static Double seconds(Date from, Date to) {
        return (to.getTime() - from.getTime()) / 1000d;
    }

    public static String getCurrentTimeStamp() {
        return dateToString(Calendar.getInstance().getTime());
    }

    public static String dateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(Context.getStringDateFormat());
        return sdf.format(date);
    }
}
