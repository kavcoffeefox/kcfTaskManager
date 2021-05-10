package ru.kavcoffeefox.kcftaskmanager.component.custom_list_cell;

import javafx.scene.control.ListCell;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;

public class TaskListCell extends ListCell<Task> {

    @Override
    protected void updateItem(Task task, boolean b) {
        super.updateItem(task, b);
        if (b || task == null) {
            setText("");
        } else {
            setText(task.getName());
        }
    }
}