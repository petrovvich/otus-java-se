package ru.petrovvich.study.handler;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSetHandler<T> implements Handler<T> {

    Class<T> type;

    public ResultSetHandler(Class<T> type) {
        this.type = type;
    }

    public T handle(final ResultSet rs) throws SQLException {
        if (rs.next()) {
            T instance = newInstance(type);
            return createObject(rs, instance);
        }
        return null;
    }

    private T newInstance(Class<? extends T> type) throws SQLException {
        try {
            return type.newInstance();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    private T createObject(final ResultSet rs, T instance) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();
        try {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                Field field = getFieldByName(instance, metaData.getColumnName(i));
                field.setAccessible(true);
                field.set(instance, rs.getObject(i));
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return instance;
    }


    public static <T> Field getFieldByName(T object, String name) throws SQLException {
        Field field = null;
        try {
            try {
                field = object.getClass().getDeclaredField(name);
            } catch (Exception e) {
                field = object.getClass().getSuperclass().getDeclaredField(name);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return field;
    }
}
