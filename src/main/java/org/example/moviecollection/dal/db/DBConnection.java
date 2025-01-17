package org.example.moviecollection.dal.db;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;

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

}
