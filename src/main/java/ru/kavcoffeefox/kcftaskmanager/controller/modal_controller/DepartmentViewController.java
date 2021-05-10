package ru.kavcoffeefox.kcftaskmanager.controller.modal_controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.kavcoffeefox.kcftaskmanager.controller.AbstractController;
import ru.kavcoffeefox.kcftaskmanager.entity.Department;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;

import java.net.URL;
import java.util.ResourceBundle;

public class DepartmentViewController extends AbstractController {
    @FXML
    public TextField shortName;
    @FXML
    public TextArea fullName;
    private Department department;

    @Override
    public void setItem(SimpleItem item) {
        this.department = (Department) item;

        shortName.setText(department.getName() == null ? "" : department.getName());
        fullName.setText(department.getFullname() == null ? "" : department.getFullname());
    }

    @FXML
    public void handleSave(){
        department.setName(shortName.getText() != null ? shortName.getText() : "");
        department.setFullname(fullName.getText() != null ? fullName.getText() : "");
        setOkClicked(true);
        closeDialogView();
    }

    @FXML
    public void handleCansel(){
        setOkClicked(false);
        closeDialogView();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
