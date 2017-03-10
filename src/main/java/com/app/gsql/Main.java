package com.app.gsql;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, SQLException {

        //String input = args[0];
        //int executions = Integer.valueOf(args[1]);
        //int repetitions = Integer.valueOf(args[2]);

        //Meter m = new Meter(input, executions, repetitions);

        //m.startTest();

        //m.results();

        //EnergyCheckUtils.dealloc();
        
        Adapter a = new Adapter("localhost", 7687, "", "neo4j", "que34almeida25");
        
        System.out.println(a.start_connection(DBMS.NEO4J));
     
        a.executeQuery(DBMS.NEO4J, "", null);
    }
}
