package ru.kavcoffeefox.kcftaskmanager.service;



import ru.kavcoffeefox.kcftaskmanager.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskManager extends Manager<Task,Integer> {

    boolean add(Task task);

    Task get(Integer id);

    boolean delete(Integer id);

    boolean update(Integer id, Task task);

    boolean complete(Integer id);

    List<Task> getAll();
    List<Task> tasks(LocalDate deadline);
    List<Task> tasks(LocalDate startDate, LocalDate endDate);

}
