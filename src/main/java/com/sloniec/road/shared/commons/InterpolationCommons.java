package com.sloniec.road.shared.commons;

import com.sloniec.road.module.result.SingleSpeedResult;
import com.sloniec.road.shared.Context;
import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;

public class InterpolationCommons {

    public static UnivariateFunction interpolatedFunction(List<Waypoint> waypoints)
        throws NonMonotonicSequenceException {
        double[] time = toArray(getInterpolationX(waypoints));
        double[] speed = toArray(getInterpolationY(waypoints));
        if (time.length == 1) {
            return x -> speed[0];
        }
        return new PolynomialFunctionLagrangeForm(time, speed);
    }

    public static List<SingleSpeedResult> generateResults(UnivariateFunction function, Double begin, Double end) {
        List<SingleSpeedResult> results = new ArrayList<>();
        for (Double i = begin; i < end; i += Context.getStep()) {
            Double speed = function.value(i);
            Date time = new Date(i.longValue());
            SingleSpeedResult result = new SingleSpeedResult(speed, time);
            results.add(result);
        }
        return results;
    }

    private static List<Double> getInterpolationX(List<Waypoint> waypoints) {
        List<Double> times = new ArrayList<>();
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Double time = TimeCommons.halfBetween(waypoints.get(i), waypoints.get(i + 1));
            times.add(time);
        }
        return times;
    }

    private static List<Double> getInterpolationY(List<Waypoint> waypoints) {
        List<Double> speeds = new ArrayList<>();
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Double dist = DistanceCommons.distance(waypoints.get(i), waypoints.get(i + 1));
            Double time = TimeCommons.seconds(waypoints.get(i), waypoints.get(i + 1));
            Double speed = dist / time;
            speeds.add(speed);
        }
        return speeds;
    }

    private static double[] toArray(List<Double> list) {
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }
}
