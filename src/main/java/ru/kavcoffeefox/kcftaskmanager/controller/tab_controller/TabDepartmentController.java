package ru.kavcoffeefox.kcftaskmanager.controller.tab_controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.kavcoffeefox.kcftaskmanager.controller.AbstractController;
import ru.kavcoffeefox.kcftaskmanager.entity.Department;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.DepartmentManagerHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.utils.DepartmentListCell;

import java.net.URL;
import java.util.ResourceBundle;

public class TabDepartmentController extends AbstractController {

    @FXML
    private ListView<Department> departments;

    private final Manager<Department, Integer> departamentManager = DepartmentManagerHibernateImpl.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departments.setCellFactory(cell -> new DepartmentListCell());
        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemDelete = new MenuItem("Удалить");
        itemDelete.setOnAction(event -> {
            if (departments.getSelectionModel().getSelectedItem() != null) {
                departamentManager.delete(departments.getSelectionModel().getSelectedItem().getId());
                departments.getItems().remove(departments.getSelectionModel().getSelectedItem());
                departments.refresh();
            }
        });
        MenuItem itemAdd = new MenuItem("Добавить");
        itemAdd.setOnAction(event -> {
            Department department = DepartmentManagerHibernateImpl.getInstance().showDepartmentView(this.getMainStage());
            if (department != null)
                departments.getItems().add(department);
            departments.refresh();
        });
        MenuItem itemUpdate = new MenuItem("Редактировать");
        itemUpdate.setOnAction(event -> {
            if (departments.getSelectionModel().getSelectedItem() != null) {
                Department department = departments.getSelectionModel().getSelectedItem();
                DepartmentManagerHibernateImpl.getInstance().showDepartmentView(this.getMainStage(), department);
                departments.refresh();
            }
        });
        MenuItem itemRefresh = new MenuItem("Обновить данные");
        itemRefresh.setOnAction(event -> {
            departments.setItems(FXCollections.observableArrayList(departamentManager.getAll()));
        });

        contextMenu.getItems().addAll(itemDelete, itemAdd, itemUpdate, itemRefresh);
        departments.setContextMenu(contextMenu);

        departments.setItems(FXCollections.observableArrayList(departamentManager.getAll()));
    }

}
