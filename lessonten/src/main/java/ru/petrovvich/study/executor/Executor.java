package ru.petrovvich.study.executor;

import java.sql.SQLException;
import java.util.List;

public interface Executor<T> {

    void save(List<T> objectData) throws SQLException, IllegalAccessException;

    <T> T load(long id, Class<T> clazz) throws SQLException;
}
