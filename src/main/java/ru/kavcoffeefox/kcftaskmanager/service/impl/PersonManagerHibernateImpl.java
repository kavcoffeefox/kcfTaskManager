package ru.kavcoffeefox.kcftaskmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.kavcoffeefox.kcftaskmanager.dao.PersonDAO;
import ru.kavcoffeefox.kcftaskmanager.dao.PersonDAOHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;

import java.util.List;

@Slf4j
public class PersonManagerHibernateImpl implements Manager<Person, Integer> {
    private volatile static PersonManagerHibernateImpl INSTANCE;
    private final PersonDAO personDAO;

    private PersonManagerHibernateImpl() {
        personDAO = new PersonDAOHibernateImpl();
    }

    @Override
    public boolean add(Person person) {
        return personDAO.addPerson(person);
    }

    @Override
    public Person get(Integer id) {
        return personDAO.getPerson(id);
    }

    @Override
    public boolean delete(Integer id) {
        return personDAO.deletePerson(id);
    }

    @Override
    public boolean update(Integer id, Person item) {
        return personDAO.updatePerson(id, item);
    }

    @Override
    public List<Person> getAll() {
        return personDAO.getAllPerson();
    }

    public static PersonManagerHibernateImpl getInstance() {
        if (INSTANCE == null)
            synchronized (PersonManagerHibernateImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new PersonManagerHibernateImpl();
                }
            }
        return INSTANCE;
    }
}
