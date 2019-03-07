package ru.petrovvich.study.service;

import ru.petrovvich.study.annotations.Id;
import ru.petrovvich.study.connection.ConnectionConfiguration;
import ru.petrovvich.study.executor.Executor;
import ru.petrovvich.study.executor.ExecutorImpl;
import ru.petrovvich.study.model.User;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static ru.petrovvich.study.handler.ResultSetHandler.getFieldByName;

public class UserServiceImpl implements UserService {

    private ConnectionConfiguration connectionConfiguration;

    public UserServiceImpl(ConnectionConfiguration configuration) throws SQLException {
        this.connectionConfiguration = configuration;
        createTable(configuration.getConnection());
    }

    @Override
    public void saveUsers(User user) throws SQLException, IllegalAccessException {
        if (!checkClass(user.getClass())) {
            throw new SQLException("Class: " + user.getClass().getSimpleName() + " hasn't required id annotation");
        }
        try (Connection connection = connectionConfiguration.getConnection()) {
            ExecutorImpl<User> executor = new ExecutorImpl<>(connection);
            if (exists(user, executor)) {
                executor.update(getParams(user, new String[]{"name", "age", "id"}));
            } else {
                executor.save(getParams(user, new String[]{"name", "age"}));
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        User result = new User();
        try (Connection connection = connectionConfiguration.getConnection()) {
            ExecutorImpl<User> executor = new ExecutorImpl<>(connection);
             result = executor.load(id, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(result);
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

    private void createTable(Connection connection) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement("CREATE TABLE User (id INT, name VARCHAR(255), age int )")) {
            stmt.executeUpdate();
        }
    }

    private <T> boolean exists(T user, Executor executor) throws SQLException, IllegalAccessException {
        Field field = getFieldByName(user, "id");
        field.setAccessible(true);
        return executor.load((Integer) field.get(user), user.getClass()) != null;
    }

    private List getParams(Object obj, String[] names) throws SQLException {
        List params = new ArrayList();
        try {
            for (String name : names) {
                Field field = getFieldByName(obj, name);
                field.setAccessible(true);
                params.add(field.get(obj));
            }
        } catch (Exception e) {
            throw new SQLException(e);
        }
        return params;
    }
}
