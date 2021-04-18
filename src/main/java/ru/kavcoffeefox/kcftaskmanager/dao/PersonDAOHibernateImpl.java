package ru.kavcoffeefox.kcftaskmanager.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import ru.kavcoffeefox.kcftaskmanager.database.HibernateConnection;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;

import java.util.List;

@Slf4j
public class PersonDAOHibernateImpl implements PersonDAO{
    @Override
    public boolean addPerson(Person person) {
        try(Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()){
            session.beginTransaction();
            session.persist(person);
            session.getTransaction().commit();
            return true;
        }
        catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public Person getPerson(Integer id) {
        try(Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()){
            session.beginTransaction();
            Person person = session.get(Person.class, id);
            session.getTransaction().commit();
            return person;
        }
        catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public boolean deletePerson(Integer id) {
        try(Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            Person person = session.get(Person.class, id);
            session.delete(person);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updatePerson(Integer id, Person person) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            Person localPerson = session.get(Person.class, id);
            localPerson.setFirstName(person.getFirstName());
            localPerson.setLastName(person.getLastName());
            localPerson.setPatronymic(person.getPatronymic());
            localPerson.setBirthDay(person.getBirthDay());
            localPerson.setDepartment(person.getDepartment());
            localPerson.setRank(person.getRank());
            localPerson.setPosition(person.getPosition());
            localPerson.setTasks(person.getTasks());
            session.saveOrUpdate(localPerson);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Person> getAllPerson() {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            List<Person> persons = session.createQuery("from Person").getResultList();
            session.getTransaction().commit();
            return persons;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
