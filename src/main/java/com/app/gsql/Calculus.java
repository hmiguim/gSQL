package com.app.gsql;

import java.util.ArrayList;

public class Calculus {

    public static double min(ArrayList<Double> values) {
        double min = values.get(0);

        for (double v : values) {
            if (v <= min) {
                min = v;
            }
        }

        return min;
    }

    public static double max(ArrayList<Double> values) {
        double max = values.get(0);

        for (double v : values) {
            if (v > max) {
                max = v;
            }
        }

        return max;
    }

    public static double average(ArrayList<Double> values) {
        double avg = 0;

        for (double v : values) {
            if (v >= 0) {
                avg += v;
            }
        }

        return (avg / (double) values.size());
    }

    public static double std_deviation(ArrayList<Double> values, double avg) {
        double std_dev = 0;

        for (double v : values) {
            std_dev += (Math.pow(v - avg, 2));
        }

        if (values.size() == 1) {
            return 0;
        }

        return Math.sqrt(std_dev / (values.size() - 1));
    }
}
