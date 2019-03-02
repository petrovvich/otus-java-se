package ru.petrovvich.study.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class H2Connection implements ConnectionConfiguration {

    private static final String URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL);
        connection.setAutoCommit(false);
        return connection;
    }
}
