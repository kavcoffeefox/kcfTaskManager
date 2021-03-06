package ru.kavcoffeefox.kcftaskmanager.component.custom_list_cell;

import javafx.scene.control.ListCell;
import ru.kavcoffeefox.kcftaskmanager.entity.Department;

public class DepartmentListCell extends ListCell<Department> {
    @Override
    protected void updateItem(Department department, boolean b) {
        super.updateItem(department, b);
        if (b || department == null) {
            setText("");
        } else {
            setText(department.getName());
        }
    }
}
