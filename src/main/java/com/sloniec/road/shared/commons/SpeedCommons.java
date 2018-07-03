package com.sloniec.road.shared.commons;

import com.sloniec.road.shared.gpxparser.modal.Waypoint;
import com.sloniec.road.module.result.SingeSpeedResult;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionLagrangeForm;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;

public class SpeedCommons {

    private final List<Waypoint> waypoints;
    private final UnivariateFunction function;
    private final Double step;

    public SpeedCommons(List<Waypoint> waypoints, Double step) throws NonMonotonicSequenceException {
        this.waypoints = waypoints;
        function = speedInterpolatedFunc(waypoints);
        this.step = step;
    }

    public List<SingeSpeedResult> calculateSpeeds() {
        List<SingeSpeedResult> results = new ArrayList<>();
        Double begin = TimeCommons.halfBetween(waypoints.get(0), waypoints.get(1));
        Double end = TimeCommons.halfBetween(waypoints.get(waypoints.size() - 2), waypoints.get(waypoints.size() - 1));
        for (Double i = begin; i < end; i += step) {
            Double speed = function.value(i);
            Date time = new Date(i.longValue());
            SingeSpeedResult result = new SingeSpeedResult(speed, time);
            results.add(result);
        }
        return results;
    }

    private UnivariateFunction speedInterpolatedFunc(List<Waypoint> waypoints) throws NonMonotonicSequenceException {
        double[] time = toArray(getInterpolationX(waypoints));
        double[] speed = toArray(getInterpolationY(waypoints));
        if (time.length == 1) {
            return x -> speed[0];
        }
        return new PolynomialFunctionLagrangeForm(time, speed);
    }

    private List<Double> getInterpolationX(List<Waypoint> waypoints) {
        List<Double> times = new ArrayList<>();
        for (int i = 0; i < waypoints.size() - 1; i++) {
            Double time = TimeCommons.halfBetween(waypoints.get(i), waypoints.get(i + 1));
            times.add(time);
        }
        return times;
    }

    private List<Double> getInterpolationY(List<Waypoint> waypoints) {
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
