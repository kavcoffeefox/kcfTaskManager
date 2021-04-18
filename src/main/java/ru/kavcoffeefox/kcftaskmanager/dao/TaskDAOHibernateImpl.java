package ru.kavcoffeefox.kcftaskmanager.dao;

import ru.kavcoffeefox.kcftaskmanager.entity.Task;

import java.time.LocalDate;
import java.util.List;

public class TaskDAOHibernateImpl implements TaskDAO{
    @Override
    public boolean addTask(Task task) {
        return false;
    }

    @Override
    public Task getTask(Integer id) {
        return null;
    }

    @Override
    public boolean deleteTask(Integer id) {
        return false;
    }

    @Override
    public boolean updateTask(Integer id, Task task) {
        return false;
    }

    @Override
    public List<Task> getAllTask() {
        return null;
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
