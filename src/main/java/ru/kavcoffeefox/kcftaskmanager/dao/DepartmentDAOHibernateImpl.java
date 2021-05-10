package ru.kavcoffeefox.kcftaskmanager.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import ru.kavcoffeefox.kcftaskmanager.database.HibernateConnection;
import ru.kavcoffeefox.kcftaskmanager.entity.Department;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DepartmentDAOHibernateImpl implements DepartmentDAO{
    @Override
    public boolean add(Department item) {
        try(Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()){
            session.beginTransaction();
            session.merge(item);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Department get(Integer id) {
        try(Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()){
            session.beginTransaction();
            Department department = session.get(Department.class, id);
            session.getTransaction().commit();
            return department;
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        try(Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()){
            session.beginTransaction();
            Department department = session.get(Department.class, id);
            session.delete(department);
            session.getTransaction().commit();
            return true;
        } catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Integer id, Department item) {
        try(Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()){
            session.beginTransaction();
            Department department = session.get(Department.class, id);
            department.setName(item.getName());
            department.setFullname(item.getFullname());
            session.saveOrUpdate(department);
            session.getTransaction().commit();
            return true;
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Department> getAll() {
        try(Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()){
            session.beginTransaction();
            List<Department> list = session.createQuery("from Department").getResultList();
            session.getTransaction().commit();
            return list;
        }catch (Exception e){
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
