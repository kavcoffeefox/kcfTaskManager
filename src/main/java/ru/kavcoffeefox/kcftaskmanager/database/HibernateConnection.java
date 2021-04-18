package ru.kavcoffeefox.kcftaskmanager.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;

public class HibernateConnection {
    private volatile static HibernateConnection INSTANCE;
    private volatile static SessionFactory factory;

    private HibernateConnection(){
        factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Person.class)
                .buildSessionFactory();
    }

    public static HibernateConnection getInstance(){
        if (INSTANCE == null)
            synchronized (HibernateConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HibernateConnection();
                }
            }
        return INSTANCE;
    }

    public SessionFactory getFactory() {
        return factory;
    }
}
