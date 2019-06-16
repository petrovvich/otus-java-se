package ru.petrovich.study.backend.service;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.petrovich.study.backend.model.AddressDataSet;
import ru.petrovich.study.backend.model.EmptyDataSet;
import ru.petrovich.study.backend.model.PhoneDataSet;
import ru.petrovich.study.backend.model.UserDataSet;
import ru.petrovich.study.backend.dao.UserDataSetDAO;

import java.util.List;
import java.util.function.Function;

public class DBServiceHibernateImpl implements DBService {
    private final SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(DBServiceHibernateImpl.class);

    public DBServiceHibernateImpl() {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSet.class);
        configuration.addAnnotatedClass(PhoneDataSet.class);
        configuration.addAnnotatedClass(EmptyDataSet.class);
        configuration.addAnnotatedClass(AddressDataSet.class);
        sessionFactory = createSessionFactory(configuration);
    }

    public DBServiceHibernateImpl(Configuration configuration) {
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public String getLocalStatus() {
        return runInSession(session -> session.getTransaction().getStatus().name());
    }

    public void save(UserDataSet dataSet) {
        try (Session session = sessionFactory.openSession()) {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            dao.save(dataSet);
            session.save(new EmptyDataSet());
        }
    }

    public UserDataSet read(long id) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            UserDataSet object = dao.read(id);
            Hibernate.initialize(object);
            return object;
        });
    }

    public UserDataSet readByName(String name) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readByName(name);
        });
    }

    public UserDataSet readByNameAndPassword(String name, String password) {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readByNameAndPassword(name, password);
        });
    }

    public List<UserDataSet> readAll() {
        return runInSession(session -> {
            UserDataSetDAO dao = new UserDataSetDAO(session);
            return dao.readAll();
        });
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private <R> R runInSession(Function<Session, R> function) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            R result = function.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            logger.warn("Exception occured when runInSession {}", e.getLocalizedMessage());
            transaction.rollback();
            return null;
        }
    }
}
