package ru.petrovvich.study;

import ru.petrovvich.study.model.AddressDataSet;
import ru.petrovvich.study.model.PhoneDataSet;
import ru.petrovvich.study.model.UserDataSet;
import ru.petrovvich.study.service.DBService;
import ru.petrovvich.study.service.DBServiceHibernateImpl;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        DBService dbService = new DBServiceHibernateImpl();

        String status = dbService.getLocalStatus();
        System.out.println("Status: " + status);

        dbService.save(new UserDataSet("tully", new PhoneDataSet("12345"), new AddressDataSet("Новосибирск", "Ленина", "12")));
        dbService.save(new UserDataSet("sully", new PhoneDataSet("67890"), new AddressDataSet()));

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
