package ru.petrovvich.study;

import ru.petrovvich.study.model.PhoneDataSet;
import ru.petrovvich.study.model.UserDataSet;
import ru.petrovvich.study.service.DBService;
import ru.petrovvich.study.service.DBServiceImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        dbService.save(new UserDataSet("tully", new PhoneDataSet("12345")));
        dbService.save(new UserDataSet("sully", new PhoneDataSet("67890")));

        UserDataSet dataSet = dbService.read(1);
        System.out.println(dataSet);

        dataSet = dbService.readByName("sully");
        System.out.println(dataSet);

        List<UserDataSet> dataSets = dbService.readAll();
        for (UserDataSet userDataSet : dataSets) {
            System.out.println(userDataSet);
        }

        dbService.shutdown();

    }

}
