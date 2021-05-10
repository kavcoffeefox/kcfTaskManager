package ru.kavcoffeefox.kcftaskmanager.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import ru.kavcoffeefox.kcftaskmanager.database.HibernateConnection;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;

import java.time.LocalDate;
import java.util.List;

@Slf4j
public class TaskDAOHibernateImpl implements TaskDAO {

    @Override
    public boolean addTask(Task task) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.saveOrUpdate(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Task getTask(Integer id) {
        Task task;
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            task = session.get(Task.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return task;
    }

    @Override
    public boolean deleteTask(Integer id) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            Task task = session.get(Task.class, id);
            session.delete(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean updateTask(Integer id, Task task) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            Task localTask = session.get(Task.class, id);
            localTask.setComplete(task.isComplete());
            localTask.setDeadline(task.getDeadline());
            localTask.setDescription(task.getDescription());
            localTask.setExecutors(task.getExecutors());
            localTask.setName(task.getName());
            localTask.setPeriod(task.getPeriod());
            localTask.setType(task.getType());
            localTask.setTags(task.getTags());
            session.saveOrUpdate(localTask);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Task> getAllTask() {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            List<Task> tasks = session.createQuery("from Task").getResultList();
            tasks.forEach( task -> {
                log.info(task.getExecutors().toString());
                log.info(task.getTags().toString());
                log.info(task.getDocuments().toString());
            });
            session.getTransaction().commit();
            return tasks;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Task> tasks(LocalDate deadline) {
        return null;
    }

    @Override
    public List<Task> tasks(LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
