package com.app.adapters;

public class Config {

    private final String host, dbname, user, password;
    private final DBMS dbms;
    private final long port;

    public Config(String host, long port, String dbname, String user, String password, String dbms) {
        this.host = host;
        this.dbname = dbname;
        this.user = user;
        this.password = password;
        this.port = port;
        this.dbms = cast(dbms);
    }
    
    public String getHost() {
        return host;
    }

    public String getDbname() {
        return dbname;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
    
    public long getPort() {
        return this.port;
    }
    
    public DBMS getDBMS() {
        return this.dbms;
    }

    @Override
    public String toString() {
        return "Config{" + "host=" + host + ", dbname=" + dbname + ", user=" + user + ", password=" + password + ", dbms=" + dbms + ", port=" + port + '}';
    }
    
    private DBMS cast(String s) {
        switch(s) {
            case "mysql": return DBMS.MYSQL;
            case "neo4j": return DBMS.NEO4J;
            case "postgresql": return DBMS.PSQL;
            default: return DBMS.MYSQL;       
        }
    }
}
