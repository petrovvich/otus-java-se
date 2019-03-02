package ru.petrovvich.study.handler;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface Handler<T> {

    T handle(ResultSet rs) throws SQLException;

}
