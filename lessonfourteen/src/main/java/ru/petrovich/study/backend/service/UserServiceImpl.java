package ru.petrovich.study.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.petrovich.study.backend.model.AddressDataSet;
import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.backend.model.PhoneDataSet;

public class UserServiceImpl implements UserService {

    private DBService dbService;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    public UserServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    public UserDataSet findByNameAndPassword(String name, String password) {
        return dbService.readByNameAndPassword(name, password);
    }

    public UserDataSet findByName(String name) {
        return dbService.readByName(name);
    }

    public UserDataSet findById(Long id) {
        return dbService.read(id);
    }

    public Integer getCountUsers() {
        return dbService.readAll().size();
    }

    public boolean saveNew(String name, String password, String phone, String city, String street, String house) {

        logger.info("Saving new user {}, {], {}, {}, {}", name, phone, city, street, house);
        dbService.save(new UserDataSet(name, password, new PhoneDataSet(phone), new AddressDataSet(city, street, house)));

        return findByName(name) != null;
    }

    public boolean exist(String login) {
        return findByName(login) != null;
    }
}
