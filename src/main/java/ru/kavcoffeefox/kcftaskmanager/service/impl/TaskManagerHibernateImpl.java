package ru.kavcoffeefox.kcftaskmanager.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.kavcoffeefox.kcftaskmanager.dao.TaskDAO;
import ru.kavcoffeefox.kcftaskmanager.dao.TaskDAOHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;
import ru.kavcoffeefox.kcftaskmanager.service.TaskManager;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class TaskManagerHibernateImpl implements TaskManager {
    private volatile static TaskManagerHibernateImpl INSTANCE;
    private final TaskDAO taskDAO;

    private Task currentTask;

    public void setCurrentTask(Task task){
        log.info(task +" is current!");
        currentTask=task;
    }

    public Task getCurrentTask(){
        log.info(String.valueOf(currentTask));
        return currentTask;
    }

    public static TaskManagerHibernateImpl getInstance() {
        if (INSTANCE == null)
            synchronized (TaskManagerHibernateImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TaskManagerHibernateImpl();
                }
            }
        return INSTANCE;
    }

    private TaskManagerHibernateImpl(){
        taskDAO = new TaskDAOHibernateImpl();
    }

    @Override
    public boolean add(Task task) {
        log.info(task + " added!");
        return taskDAO.addTask(task);
    }

    @Override
    public Task get(Integer id) {
        return taskDAO.getTask(id);
    }

    @Override
    public boolean delete(Integer id) {
        return taskDAO.deleteTask(id);
    }

    public boolean delete() {
        if (currentTask != null) {
            log.info(currentTask + " deleted!");
            if (taskDAO.deleteTask(currentTask.getId())){
                currentTask = null;
                return true;
            }else {
                log.warn("current task doesn't deleted");
            }
        }
        else {
            log.warn("current task is null");
        }
        return false;
    }


    @Override
    public boolean update(Integer id, Task task) {
        boolean isUpdate = taskDAO.updateTask(id, task);
        if (isUpdate) log.info(currentTask + " updated!");
        else log.info(currentTask + "not updated!");
        return isUpdate;
    }

    @Override
    public List<Task> getAll() {
        return taskDAO.getAllTask();
    }

    @Override
    public List<Task> tasks(LocalDate deadline) {
        return taskDAO.getAllTask().stream().filter(task -> task.getDeadline().equals(deadline)).collect(Collectors.toList());
    }

    @Override
    public List<Task> tasks(LocalDate startDate, LocalDate endDate) {
        return null;
    }
}
