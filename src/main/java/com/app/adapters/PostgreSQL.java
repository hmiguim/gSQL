package com.app.adapters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQL extends Adapter {

    private Connection connection;
    
    public PostgreSQL(String host, long port, String dbname, String user, String password) {
        super(host, port, dbname, user, password);
    }

    @Override
    public boolean connect() {
        StringBuilder url = new StringBuilder("jdbc:postgresql://");
        url.append(this.host)
                .append(":")
                .append(this.port)
                .append("/")
                .append(this.dbname);

        try {
            this.connection = DriverManager.getConnection(url.toString(), this.user, this.password);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, ex.getMessage());
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
            Statement stat = this.connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            
            stat.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (NullPointerException ex) {
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, "No active connections");
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
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, ex.getMessage());
        } catch (NullPointerException ex) {
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, "No active connections");
        }
    }
    
    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(PostgreSQL.class.getName()).log(Level.SEVERE, "No active connections");
        }
    }

}
