package ru.kavcoffeefox.kcftaskmanager.dao;

import ru.kavcoffeefox.kcftaskmanager.entity.Person;

import java.util.List;

public interface PersonDAO {
    boolean addPerson(Person person);

    Person getPerson(Integer id);

    boolean deletePerson(Integer id);

    boolean updatePerson(Integer id, Person person);

    List<Person> getAllPerson();
}
