package com.app.gsql;

import java.util.ArrayList;

public class Measure {

    private ArrayList<Double> energy;
    private ArrayList<Double> power;
    private ArrayList<Double> performance;

    public Measure() {
        this.energy = new ArrayList<>();
        this.power = new ArrayList<>();
        this.performance = new ArrayList<>();
    }

    public void add(double energy, double power, double time) {

        this.energy.add(energy);
        this.power.add(power);
        this.performance.add(time);

    }

    public ArrayList<Double> getEnergyMeasurements() {
        return this.energy;
    }

    public ArrayList<Double> getPowerMeasurements() {
        return this.power;
    }

    public ArrayList<Double> getPerformanceMeasurements() {
        return this.performance;
    }

}
