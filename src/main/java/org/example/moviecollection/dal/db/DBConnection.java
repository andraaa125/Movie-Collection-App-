package org.example.moviecollection.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {
    public Connection getConnection() throws SQLServerException {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setDatabaseName("MovieCollectionApp_ABHJ");
        ds.setUser("CSe2024b_e_0");
        ds.setPassword("CSe2024bE0!24");
        ds.setServerName("EASV-DB4");
        ds.setPortNumber(1433);
        ds.setTrustServerCertificate(true);
        return ds.getConnection();
    }

/*    private Connection connection;

    public DBConnection() throws SQLException {
        reconnect();
    }

    private void reconnect() throws SQLException {
        try {
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setDatabaseName("MovieCollectionApp_ABHJ");
            ds.setUser("CSe2024b_e_0");
            ds.setPassword("CSe2024bE0!24");
            ds.setServerName("EASV-DB4");
            ds.setPortNumber(1433);
            ds.setTrustServerCertificate(true);

            this.connection = ds.getConnection();
            System.out.println("Connection to the database has been established!");

        } catch (SQLException e) {
            System.out.println("Connection to the database has failed!");
            e.printStackTrace();
            throw e;
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            System.out.println("Reconnecting to the database...");
            reconnect();
        }
        return connection;
    }*/
}
