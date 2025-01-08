package org.example.moviecollection.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

    private Connection connection;

    public DBConnection() throws SQLException {
        reconnect();
    }

    private void reconnect() throws SQLException {
        try {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setDatabaseName("Movie Collection App");
            ds.setUser("username");
            ds.setPassword("password");
            ds.setServerName("EASV-DB4");
            ds.setPortNumber(1433);
            ds.setTrustServerCertificate(true);

            this.connection = ds.getConnection();
            System.out.println("Connection established!");

        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
            throw e;
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            System.out.println("Reconnecting to the database");
            reconnect();
        }
        return connection;
    }
}
