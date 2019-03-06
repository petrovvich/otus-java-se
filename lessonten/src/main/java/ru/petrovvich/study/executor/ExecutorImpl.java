package ru.petrovvich.study.executor;

import ru.petrovvich.study.annotations.Id;
import ru.petrovvich.study.handler.ResultSetHandler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.petrovvich.study.handler.ResultSetHandler.getFieldByName;

public class ExecutorImpl<T> implements Executor<T> {

    private final Connection connection;
    private static final String SELECT_USER = "SELECT * FROM User WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE User SET name = ?, age = ? WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO User (name, age) VALUES (?, ?)";

    public ExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(T objectData) throws SQLException, IllegalAccessException {
        if (!exists(objectData)) {
            update(UPDATE_USER, objectData, "name", "age", "id");
        } else {
            insert(INSERT_USER, objectData, "name", "age");
        }
    }

    @Override
    public <T> T load(long id, Class<T> clazz) throws SQLException {
        if (checkClass(clazz)) {
            ResultSetHandler<T> handler = new ResultSetHandler<>(clazz);
            select(SELECT_USER, handler, id);
        } else {
            throw new NotImplementedException();
        }
        return (T) Optional.empty();
    }

    private <T> T select(String sql, ResultSetHandler<T> handler, Object... params) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            prepare(stmt, params);
            ResultSet rs = stmt.executeQuery();
            return handler.handle(rs);
        }
    }

    private <T> int update(String sql, T user, String... names) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            prepare(stmt, getParams(user, names));
            int updates = stmt.executeUpdate();
            if (updates == 0) {
                throw new SQLException("User not updated");
            }
            return updates;
        }
    }

    private void prepare(PreparedStatement statement, Object... params) throws SQLException {
        ParameterMetaData metadata = statement.getParameterMetaData();
        int count = metadata.getParameterCount();
        if (count != params.length) {
            throw new SQLException();
        }
        for (int i = 0; i < count; i++) {
            statement.setObject(i + 1, params[i]);
        }
    }

    private <T> int insert(String sql, T user, String... names) throws SQLException, IllegalAccessException {
        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            prepare(stmt, getParams(user, names));
            int inserts = stmt.executeUpdate();
            if (inserts == 0) {
                throw new SQLException("User not created");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    getFieldByName(user, "id").set(user, generatedKeys.getLong(1));
                } else {
                    throw new SQLException("User ID not generated");
                }
            }
            return inserts;
        }
    }

    private Object[] getParams(Object obj, String[] names) throws SQLException {
        Object[] params = new Object[names.length];
        try {
            for (int i = 0; i < names.length; i++) {
                Field field = getFieldByName(obj, names[i]);
                field.setAccessible(true);
                params[i] = field.get(obj);
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return params;
    }

    private boolean checkClass(Class clazz) {
        boolean result = false;

        List<Field> fields = Arrays.asList(clazz.getDeclaredFields());

        for (Field f : fields) {
            f.setAccessible(true);
            if (checkFieldAnnotations(f, Id.class)) {
                result = true;
            }
        }
        return result;
    }

    private static boolean checkFieldAnnotations(Field field, Class annotationClass) {
        return field.getAnnotation(annotationClass) != null;
    }

    private <T> boolean exists(T user) throws SQLException, IllegalAccessException {
        Field field= getFieldByName(user, "id");
        field.setAccessible(true);
        return load((Integer) field.get(user), user.getClass()) != null;
    }
}
