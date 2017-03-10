package com.app.gsql;

public class Result {

    private final double avg;
    private final double min;
    private final double max;
    private final double std_dev;

    public Result() {
        this.avg = 0;
        this.min = 0;
        this.max = 0;
        this.std_dev = 0;
    }

    public Result(double avg, double std, double min, double max) {
        this.avg = avg;
        this.min = min;
        this.max = max;
        this.std_dev = std;
    }

    public double getAvg() {
        return avg;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getStdDev() {
        return std_dev;
    }
}
