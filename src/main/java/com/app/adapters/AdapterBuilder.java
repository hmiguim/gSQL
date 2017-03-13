package com.app.adapters;

public class AdapterBuilder {
    
    private String host, dbname, user, password;
    private long port;
    
    public AdapterBuilder setHost(String host) {
        this.host = host;
        return this;
    }

    public AdapterBuilder setDBname(String dbname) {
        this.dbname = dbname;
        return this;
    }

    public AdapterBuilder setUser(String user) {
        this.user = user;
        return this;
    }

    public AdapterBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public AdapterBuilder setPort(long port) {
        this.port = port;
        return this;
    }
    
    public Adapter build(DBMS type) {
        
        switch(type) {
            case NEO4J:
                return new Neo4j(this.host, this.port, this.dbname, this.user, this.password);
            case MYSQL:
                return new MySQL(this.host, this.port, this.dbname, this.user, this.password);
            case PSQL:
                return new PostgreSQL(this.host, this.port, this.dbname, this.user, this.password);
        }
        
        return null;
    }
}
