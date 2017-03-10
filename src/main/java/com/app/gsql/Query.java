package com.app.gsql;

import java.util.ArrayList;

public class Query {

    private final String statment;
    private final ArrayList<String> opts;

    private final Measure measures;
    private Result energy_results;
    private Result power_results;
    private Result performance_results;

    public Query(String statment, ArrayList<String> opts) {
        this.statment = statment;
        this.opts = opts;
        this.measures = new Measure();
    }

    public String getStatment() {
        return this.statment;
    }

    public ArrayList<String> getOptions() {
        return this.opts;
    }

    public void addMeasure(double joules, double power, double time) {
        this.measures.add(joules, power, time);
    }

    public Measure getMeasures() {
        return this.measures;
    }

    public void setEnergyResults(Result r) {
        this.energy_results = r;
    }

    public void setPowerResults(Result r) {
        this.power_results = r;
    }

    public void setPerformanceResults(Result r) {
        this.performance_results = r;
    }

    public Result getEnergyResults() {
        return this.energy_results;
    }

    public Result getPowerResults() {
        return this.power_results;
    }

    public Result getPerformanceResults() {
        return this.performance_results;
    }

}
