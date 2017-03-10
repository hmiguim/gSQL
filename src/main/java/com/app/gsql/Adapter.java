package com.app.gsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;

public class Adapter {

    private String host, dbname, user, password;
    private int port;
    private Connection conn;
    private Session session;

    public Adapter() {
        this.conn = null;
        this.session = null;
    }

    public Adapter(String host, int port, String dbname, String user, String password) {
        this.host = host;
        this.dbname = dbname;
        this.user = user;
        this.password = password;
        this.port = port;
        this.conn = null;
    }

    public boolean start_connection(DBMS type) {

        switch (type) {
            case NEO4J:
                return neo4j_connection();
            case MYSQL:
                return mysql_connection();
            default:
                return false;
        }
    }

    private boolean mysql_connection() {
        StringBuilder url = new StringBuilder("jdbc:mysql://");
        url.append(this.host).append(":").append(this.port).append("/").append(this.dbname);

        try {
            this.conn = DriverManager.getConnection(url.toString(), this.user, this.password);
        } catch (SQLException ex) {
            System.out.println("Connection Failed! Check output console");
            return false;
        }
        return this.test_connection();
    }

    private boolean neo4j_connection() {

        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic(this.user, this.password));

        session = driver.session();

        return this.session.isOpen();

    }

    private boolean test_connection() {
        return this.conn != null;
    }

    public void executeQuery(DBMS type, String statment, ArrayList<String> options) throws SQLException {

        switch (type) {
            case MYSQL:
                Statement stat = this.conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

                for (String opt : options) {
                    stat.execute(opt);
                }
                stat.execute(statment);
                break;

            case NEO4J:
                try (Transaction t = session.beginTransaction()) {
                    StatementResult result = t.run("MATCH(F:FactInternetSales)-[:ORDER_AT]->(D),(F:FactInternetSales)-[:PRODUCT]->(P)-[:SUBCATEGORY]->(S)-[:CATEGORY]->(C)\n"
                            + "WHERE D.CalendarYear = 2014\n"
                            + "RETURN C.EnglishProductCategoryName as Category, COUNT(F.SalesAmount) as Nr, SUM(F.SalesAmount) as Total\n"
                            + "ORDER BY C.EnglishProductCategoryName ASC;");

                    while (result.hasNext()) {
                        Record record = result.next();
                        System.out.println(record.get("Category").asString());
                    }
                }
        }

    }

    public void close_connection() throws SQLException {
        this.conn.close();
    }
}
