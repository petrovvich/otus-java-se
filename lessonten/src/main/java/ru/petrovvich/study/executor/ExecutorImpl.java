package ru.petrovvich.study.executor;

import ru.petrovvich.study.handler.ResultSetHandler;
import ru.petrovvich.study.model.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ExecutorImpl<T> implements Executor<T> {

    private final Connection connection;
    private static final String SELECT_USER = "SELECT * FROM User WHERE id = ?";
    private static final String UPDATE_USER = "UPDATE User SET name = ?, age = ? WHERE id = ?";
    private static final String INSERT_USER = "INSERT INTO User (name, age) VALUES (?, ?)";

    public ExecutorImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(List<T> objectData) throws SQLException {
        Savepoint savepoint = this.connection.setSavepoint("Before insert");
        try (PreparedStatement stmt = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            if (stmt.getParameterMetaData().getParameterCount() != objectData.size()) {
                throw new SQLException("Object data parameters count not equal request's parameters count");
            }
            for (int i = 0; i < objectData.size(); i++) {
                stmt.setString(i + 1, String.valueOf(objectData.get(i)));
            }
            stmt.executeUpdate();
            try (ResultSet resultSet = stmt.getResultSet()) {
                ResultSetHandler<User> resultSetHandler = new ResultSetHandler<>(User.class);
                resultSetHandler.handle(resultSet);
            }
        } catch (SQLException e) {
            this.connection.rollback(savepoint);
        }
    }

    public void update(List<T> objectData) throws SQLException {
        Savepoint savepoint = this.connection.setSavepoint("Before update");
        try (PreparedStatement stmt = connection.prepareStatement(UPDATE_USER)) {
            if (stmt.getParameterMetaData().getParameterCount() != objectData.size()) {
                throw new SQLException("Object data parameters count not equal request's parameters count");
            }
            for (int i = 0; i < objectData.size(); i++) {
                stmt.setString(i + 1, String.valueOf(objectData.get(i)));
            }
            stmt.executeUpdate();
            try (ResultSet resultSet = stmt.getResultSet()) {
                ResultSetHandler<User> resultSetHandler = new ResultSetHandler<>(User.class);
                resultSetHandler.handle(resultSet);
            }
        } catch (SQLException e) {
            this.connection.rollback(savepoint);
        }
    }

    @Override
    public <T> T load(long id, Class<T> clazz) throws SQLException {
        Savepoint savepoint = this.connection.setSavepoint("Before update");
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_USER)) {
            stmt.execute();
            try (ResultSet resultSet = stmt.getResultSet()) {
                ResultSetHandler<User> resultSetHandler = new ResultSetHandler<>(User.class);
                return (T) resultSetHandler.handle(resultSet);
            }
        } catch (SQLException e) {
            this.connection.rollback(savepoint);
        }

        return (T) Optional.empty();
    }

}
