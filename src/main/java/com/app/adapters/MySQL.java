package com.app.adapters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL extends Adapter {

    private Connection connection;

    public MySQL(String host, long port, String dbname, String user, String password) {
        super(host, port, dbname, user, password);
    }

    @Override
    public boolean connect() {
        StringBuilder url = new StringBuilder("jdbc:mysql://");
        url.append(this.host)
                .append(":")
                .append(this.port)
                .append("/")
                .append(this.dbname).append("?allowMultiQueries=true");

        try {
            this.connection = DriverManager.getConnection(url.toString(), this.user, this.password);
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, ex.getMessage());
            return false;
        }

        return this.testConnection();
    }

    private boolean testConnection() {
        return this.connection != null;
    }

    @Override
    public void executeQuery(String query) {
        try {
            Statement stat = this.connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            
            stat.execute(query);
            
            /*ResultSet rs = stat.executeQuery(query);
            
            while(rs.next()){
                //Retrieve by column name
                System.out.println(rs.getDouble("YearSalesVariance"));
            } */
            
            
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (NullPointerException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, "No active connections");
        }
    }

    @Override
    public void setOptions(ArrayList<String> options) {
        try {
            Statement stat = this.connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            if (options != null) {
                for (String opt : options) {
                    stat.execute(opt);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (NullPointerException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, "No active connections");
        }
    }

    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (NullPointerException ex) {
            Logger.getLogger(MySQL.class.getName()).log(Level.SEVERE, "No active connections");
        }
    }
}
