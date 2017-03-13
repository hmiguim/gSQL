package com.app.instruments;

import com.app.adapters.Adapter;
import com.app.io.ParserUtil;
import com.app.io.WriterUtil;
import java.util.ArrayList;
import java.util.Date;

public class MeasureTool {

    private final String input;
    private final int executions;
    private final int repetitions;

    private final Adapter adapter;
    
    private ArrayList<Query> queries;

    public MeasureTool(String input, int executions, int repetitions, Adapter adapter) {
        this.input = input;
        this.executions = executions;
        this.repetitions = repetitions;
        this.queries = new ArrayList<>();
        this.adapter = adapter;
    }
    
    public void startTest() {

        if (!this.adapter.connect()) {
            System.out.println("Error");
            System.exit(0);
        }

        this.queries = ParserUtil.decompose(input);
        
        this.queries.forEach((q) -> {
            for (int e = 0; e < this.executions; ++e) {
                
                // IDLE CONSUME ( WEIGHT ) 
                //double idle_energy = this.idleEnergy(idle_time);
                //double idle_power = idle_energy / idle_time;
                for (int r = 0; r < this.repetitions; ++r) {

                    this.adapter.setOptions(q.getOptions());
                    
                    double start = this.getEnergy();
                    Date start_timestamp = new Date();

                    // DO A QUERY
                    this.adapter.executeQuery(q.getQuery());

                    double stop = this.getEnergy();
                    Date stop_timestamp = new Date();
                    
                    long performance = stop_timestamp.getTime() - start_timestamp.getTime();
                    
                    double time = (double) ((double) performance / (double) 1000);
                    
                    double energy = stop - start;

                    double power = energy / (double) performance;
                    //double weight = idle_power * (double) performance;
                    
                    //energy -= weight;
                    //power -= idle_power;
                    if (energy > 0) {
                        q.addMeasure(energy, power, time);
                    }
                }
            }
        });

        this.adapter.close();

    }
    
    public void results() {
        WriterUtil writer = new WriterUtil();     
        writer.setQueries(this.queries);
        writer.write(this.input);
    }

    private double getEnergy() {
        double[] energyStats = EnergyCheckUtils.getEnergyStats();
        return energyStats[0] + energyStats[1];
    }

    private double idleEnergy(int seconds) throws InterruptedException {

        double start = this.getEnergy();
        Thread.sleep(seconds * 1000);
        double end = this.getEnergy();

        return end - start;
    }
}
