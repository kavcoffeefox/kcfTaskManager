package ru.kavcoffeefox.kcftaskmanager.database;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kavcoffeefox.kcftaskmanager.entity.*;

@Slf4j
public class HibernateConnection {
    private volatile static HibernateConnection INSTANCE = null;
    private SessionFactory factory;

    private HibernateConnection(){
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Document.class)
                .addAnnotatedClass(Tag.class)
                .addAnnotatedClass(Department.class)
                .buildSessionFactory();
        log.info("Hibernate connection is create");
    }

    public static HibernateConnection getInstance(){
        if (INSTANCE == null) {
            synchronized (HibernateConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HibernateConnection();
                }
            }
        }
        return INSTANCE;
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void closeFactory(){
        factory.close();
    }
}
