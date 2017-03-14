package com.app.gsql;

import com.app.adapters.Adapter;
import com.app.adapters.AdapterBuilder;
import com.app.adapters.Config;
import com.app.instruments.EnergyCheckUtils;
import com.app.instruments.MeasureTool;
import com.app.io.ParserUtil;

public class Main {

    public static void main(String[] args) {

        String config = args[0];                    // ARG0 - database configuration file
        String input = args[1];                     // ARG1 - Test file
        int executions = Integer.valueOf(args[2]);  // ARG2 - Number of executions
        int repetitions = Integer.valueOf(args[3]); // ARG3 - Number of repetitions
        
        Config readDatabaseConfiguration = ParserUtil.readDatabaseConfiguration(config);
        
        Adapter adapter =  new AdapterBuilder().setHost(readDatabaseConfiguration.getHost())
                .setPort(readDatabaseConfiguration.getPort()) //7687 //3306 //5432
                .setDBname(readDatabaseConfiguration.getDbname())
                .setUser(readDatabaseConfiguration.getUser())
                .setPassword(readDatabaseConfiguration.getPassword())
                .build(readDatabaseConfiguration.getDBMS());
        
        
        MeasureTool m = new MeasureTool(input, executions, repetitions, adapter);
        
        m.startTest();

        m.results();
        
        EnergyCheckUtils.dealloc();
    }
}
