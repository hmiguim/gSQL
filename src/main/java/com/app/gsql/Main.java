package com.app.gsql;

import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, SQLException {

        String input = args[0];
        int executions = Integer.valueOf(args[1]);
        int repetitions = Integer.valueOf(args[2]);

        Meter m = new Meter(input, executions, repetitions);

        m.startTest(DBMS.MYSQL);

        m.results();

        EnergyCheckUtils.dealloc();
        
    }
}
