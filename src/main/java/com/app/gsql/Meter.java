package com.app.gsql;

import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

public class Meter {

    private final String input;
    private final int executions;
    private final int repetitions;

    private ArrayList<Query> queries;

    public Meter(String input, int executions, int repetitions) {
        this.input = input;
        this.executions = executions;
        this.repetitions = repetitions;
        this.queries = new ArrayList<>();
    }

    public void startTest(DBMS type) throws IOException, InterruptedException, SQLException {

        Adapter adapter = new Adapter("127.0.0.1", 5432, "postgres", "p90", "5432");

        if (!adapter.start_connection(type)) {
            System.out.println("Error");
            System.exit(0);
        }

        this.queries = Parser.decompose(input);

        for (Query q : this.queries) {
            for (int e = 0; e < executions; ++e) {

                // IDLE CONSUME ( WEIGHT ) 
                //double idle_energy = this.idleEnergy(idle_time);
                //double idle_power = idle_energy / idle_time;
                for (int r = 0; r < repetitions; ++r) {

                    double start = this.getEnergy();
                    Date start_timestamp = new Date();

                    // DO A QUERY
                    adapter.executeQuery(DBMS.MYSQL,q.getStatment(), q.getOptions());

                    double stop = this.getEnergy();
                    Date stop_timestamp = new Date();

                    long performance = (stop_timestamp.getTime() - start_timestamp.getTime()) / 1000;

                    double energy = stop - start;

                    double power = energy / (double) performance;
                    //double weight = idle_power * (double) performance;

                    //energy -= weight;
                    //power -= idle_power;
                    if (energy > 0) {
                        q.addMeasure(energy, power, (double) performance);
                    }
                }
            }

        }

        adapter.close_connection();

    }

    public void results() throws IOException {

        StringBuilder builder;

        File file = new File("/home/gsd/gSQL/results/results.csv");

        FileWriter writer = new FileWriter(file, true);

        try (PrintWriter out = new PrintWriter(writer)) {
            out.println("line,joules_avg,joules_std,joules_min,joules_max,watts_avg,watts_std,watts_min,watts_max,time_avg,time_std,time_min,time_max");
            
            for (Query q : this.queries) {
                
                builder = new StringBuilder();
                Measure m = q.getMeasures();
                
                /**
                 * ************
                 *** Energy ***
                 **************
                 */
                double e_avg = Calculus.average(m.getEnergyMeasurements());
                double e_min = Calculus.min(m.getEnergyMeasurements());
                double e_max = Calculus.max(m.getEnergyMeasurements());
                double e_std = Calculus.std_deviation(m.getEnergyMeasurements(), e_avg);
                
                /**
                 * ************
                 **** Power ****
                 **************
                 */
                double p_avg = Calculus.average(m.getPowerMeasurements());
                double p_min = Calculus.min(m.getPowerMeasurements());
                double p_max = Calculus.max(m.getPowerMeasurements());
                double p_std = Calculus.std_deviation(m.getPowerMeasurements(), p_avg);
                
                /**
                 * ******************
                 **** Performance ****
                 ********************
                 */
                double t_avg = Calculus.average(m.getPerformanceMeasurements());
                double t_min = Calculus.min(m.getPerformanceMeasurements());
                double t_max = Calculus.max(m.getPerformanceMeasurements());
                double t_std = Calculus.std_deviation(m.getPerformanceMeasurements(), t_avg);
                
                builder.append(q.getStatment()).append(",");
                
                builder.append(e_avg).append(",")
                        .append(e_std).append(",")
                        .append(e_min).append(",")
                        .append(e_max).append(",");
                
                builder.append(p_avg).append(",")
                        .append(p_std).append(",")
                        .append(p_min).append(",")
                        .append(p_max).append(",");
                
                builder.append(t_avg).append(",")
                        .append(t_std).append(",")
                        .append(t_min).append(",")
                        .append(t_max);
                
                out.println(builder.toString());
            }
        }
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
