package com.app.instruments;

import com.app.statistics.Measure;
import com.app.statistics.Result;
import com.app.statistics.StatisticsUtil;
import java.util.ArrayList;

public class Query {
    
    private final String id;
    private final String query;
    private final ArrayList<String> options;
    private final Measure measures;
    
    private Result energy_results;
    private Result power_results;
    private Result performance_results;

    public Query(String id, String query, ArrayList<String> options) {
        this.id = id;
        this.query = query;
        this.options = options;
        this.measures = new Measure();
    }

    public String getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }
    
    public ArrayList<String> getOptions() {
        return options;
    }

    public void addMeasure(double joules, double power, double time) {
        this.measures.add(joules, power, time);
    }

    public String getResults() {
        StringBuilder result = new StringBuilder();
        
        ArrayList<Double> energy        = this.measures.getEnergyMeasurements();
        ArrayList<Double> power         = this.measures.getPowerMeasurements();
        ArrayList<Double> performance   = this.measures.getPerformanceMeasurements();
        
        result.append(this.id)
                .append(",")
                .append(StatisticsUtil.average(energy))
                .append(",")
                .append(StatisticsUtil.std_deviation(energy, StatisticsUtil.average(energy)))
                .append(",")
                .append(StatisticsUtil.min(energy))
                .append(",")
                .append(StatisticsUtil.max(energy))
                .append(",")
                .append(StatisticsUtil.average(power))
                .append(",")
                .append(StatisticsUtil.std_deviation(power, StatisticsUtil.average(power)))
                .append(",")
                .append(StatisticsUtil.min(power))
                .append(",")
                .append(StatisticsUtil.max(power))
                .append(",")
                .append(StatisticsUtil.average(performance))
                .append(",")
                .append(StatisticsUtil.std_deviation(performance, StatisticsUtil.average(performance)))
                .append(",")
                .append(StatisticsUtil.min(performance))
                .append(",")
                .append(StatisticsUtil.max(performance));
        
        
        return result.toString();
    }

    @Override
    public String toString() {
        return "Query{" + "id=" + id + ", query=" + query + ", options=" + options + ", measures=" + measures + ", energy_results=" + energy_results + ", power_results=" + power_results + ", performance_results=" + performance_results + '}';
    }
}
