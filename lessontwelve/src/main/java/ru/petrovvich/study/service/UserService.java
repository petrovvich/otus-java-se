package ru.petrovvich.study.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.petrovvich.study.model.UserDataSet;

public class UserService {

    private DBServiceHibernateImpl dbService = new DBServiceHibernateImpl();
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDataSet findByNameAndPassword(String name, String password) {
        return dbService.readByNameAndPassword(name, password);
    }

    public UserDataSet findByName(String name) {
        return dbService.readByName(name);
    }

    public boolean saveNew(UserDataSet user) {
        logger.info("Saving new user {}", user);
        dbService.save(user);

        return findByName(user.getName()) != null;
    }
}
