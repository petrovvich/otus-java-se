package ru.petrovvich.study.connection;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionConfiguration {

    public Connection getConnection() throws SQLException;
}
