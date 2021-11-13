package database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class DatabaseConnectionPool {
    private static DatabaseConnectionPool instance = null;
    private BasicDataSource basicDataSource = new BasicDataSource();

    private DatabaseConnectionPool() {
        basicDataSource.setUrl("jdbc:mysql://localhost:3306/bookstore?serverTimezone=UTC");
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername("root");
        basicDataSource.setPassword("root");
        basicDataSource.setMinIdle(10);
        basicDataSource.setMaxIdle(15);
        basicDataSource.setMaxOpenPreparedStatements(30);
    }

    synchronized public static DatabaseConnectionPool getInstance(){
        if (instance == null) {
            instance = new DatabaseConnectionPool();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return basicDataSource.getConnection();
    }
}
