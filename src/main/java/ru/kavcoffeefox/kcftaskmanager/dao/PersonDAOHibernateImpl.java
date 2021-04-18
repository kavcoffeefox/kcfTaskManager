package ru.kavcoffeefox.kcftaskmanager.dao;

import ru.kavcoffeefox.kcftaskmanager.entity.Person;

import java.util.List;

public class PersonDAOHibernateImpl implements PersonDAO{
    @Override
    public boolean addPerson() {
        return false;
    }

    @Override
    public Person getPerson(Integer id) {
        return null;
    }

    @Override
    public boolean deletePerson(Integer id) {
        return false;
    }

    @Override
    public boolean updatePerson(Integer id, Person person) {
        return false;
    }

    @Override
    public List<Person> getAllPerson() {
        return null;
    }
}
