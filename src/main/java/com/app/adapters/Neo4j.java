package com.app.adapters;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.exceptions.AuthenticationException;
import org.neo4j.driver.v1.exceptions.ServiceUnavailableException;

public class Neo4j extends Adapter {

    private Session session;

    public Neo4j(String host, long port, String dbname, String user, String password) {
        super(host, port, dbname, user, password);
    }

    @Override
    public boolean connect() {
        try {
            StringBuilder uri = new StringBuilder("bolt://");
            uri.append(this.host)
                    .append(":")
                    .append(this.port);

            Driver driver = GraphDatabase.driver(uri.toString(), AuthTokens.basic(this.user, this.password));

            session = driver.session();

            return this.session.isOpen();
        } catch (AuthenticationException | ServiceUnavailableException ex) {
            java.util.logging.Logger.getLogger(Neo4j.class.getName()).log(Level.SEVERE, ex.getMessage());
            return false;
        }
    }

    @Override
    public void executeQuery(String query) {
        try (Transaction t = this.session.beginTransaction()) {
            t.run(query);
        } catch (NullPointerException ex) {
            Logger.getLogger(Neo4j.class.getName()).log(Level.SEVERE, "No active connections");
        }
    }
    
    @Override
    public void setOptions(ArrayList<String> options) {
        
    }

    @Override
    public void close() {
        try {
            this.session.close();
        } catch (NullPointerException ex) {
            Logger.getLogger(Neo4j.class.getName()).log(Level.SEVERE, "No active connections");
        }
    }

}
