package ru.kavcoffeefox.kcftaskmanager.dao;

import ru.kavcoffeefox.kcftaskmanager.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskDAO {
    boolean addTask(Task task);

    Task getTask(Integer id);

    boolean deleteTask(Integer id);

    boolean updateTask(Integer id, Task task);

    List<Task> getAllTask();
    List<Task> tasks(LocalDate deadline);
    List<Task> tasks(LocalDate startDate, LocalDate endDate);

}
