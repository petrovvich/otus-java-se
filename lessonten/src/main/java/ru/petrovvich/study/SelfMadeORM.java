package ru.petrovvich.study;

import ru.petrovvich.study.connection.H2Connection;
import ru.petrovvich.study.executor.ExecutorImpl;
import ru.petrovvich.study.model.User;

import java.sql.Connection;
import java.sql.SQLException;

public class SelfMadeORM {

    public static void main(String[] args) throws SQLException, IllegalAccessException {
        try (Connection connection = new H2Connection().getConnection()) {
            ExecutorImpl executor = new ExecutorImpl(connection);

            User user = new User();
            user.setId(1);
            user.setAge(12);
            user.setName("Viktor");
            executor.save(user);
            System.out.println("User created: " + user);

            User newUser = (User) executor.load(user.getId(), User.class);
            System.out.println("User loaded: " + newUser);

            System.out.println("Objects are equal: " + user.equals(newUser));

            user.setAge(40);
            user.setName("John Doe");
            executor.save(user);
            System.out.println("User updated: " + user);

            newUser = (User) executor.load(user.getId(), User.class);
            System.out.println("User loaded: " + newUser);

            System.out.println("Objects are equal: " + user.equals(newUser));
        }
    }
}
