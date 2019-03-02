package ru.petrovvich.study.executor;

import java.sql.SQLException;

public interface Executor<T> {

    void save(T objectData) throws SQLException, IllegalAccessException;

    <T> T load(long id, Class<T> clazz) throws SQLException;
}
