package com.app.adapters;

import java.util.ArrayList;

public abstract class Adapter {

    protected final String host, dbname, user, password;
    protected final long port;

    public Adapter(String host, long port, String dbname, String user, String password) {
        this.host = host;
        this.dbname = dbname;
        this.user = user;
        this.password = password;
        this.port = port;
    }
    
    public abstract boolean connect();
    
    public abstract void executeQuery(String query);
    
    public abstract void setOptions(ArrayList<String> options);
    
    public abstract void close();
}
