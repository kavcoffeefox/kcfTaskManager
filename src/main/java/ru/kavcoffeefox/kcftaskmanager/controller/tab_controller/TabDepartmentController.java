package ru.kavcoffeefox.kcftaskmanager.controller.tab_controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.kavcoffeefox.kcftaskmanager.component.custom_list_cell.TagListCell;
import ru.kavcoffeefox.kcftaskmanager.controller.AbstractController;
import ru.kavcoffeefox.kcftaskmanager.entity.Department;
import ru.kavcoffeefox.kcftaskmanager.entity.Tag;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.DepartmentManagerHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.component.custom_list_cell.DepartmentListCell;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TagManagerHibernateImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class TabDepartmentController extends AbstractController {

    @FXML
    private ListView<Department> departments;
    @FXML
    private ListView<Tag> tags;

    private final Manager<Department, Integer> departamentManager = DepartmentManagerHibernateImpl.getInstance();
    private final Manager<Tag, Integer> tagManager = TagManagerHibernateImpl.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departments.setCellFactory(cell -> new DepartmentListCell());
        tags.setCellFactory(cell -> new TagListCell());

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
        itemRefresh.setOnAction(event -> departments.setItems(FXCollections.observableArrayList(departamentManager.getAll())));

        contextMenu.getItems().addAll(itemDelete, itemAdd, itemUpdate, itemRefresh);
        departments.setContextMenu(contextMenu);

        ContextMenu contextMenuTag = new ContextMenu();

        MenuItem itemDeleteTag = new MenuItem("Удалить");
        itemDeleteTag.setOnAction(event -> {
            if (tags.getSelectionModel().getSelectedItem() != null) {
                tagManager.delete(tags.getSelectionModel().getSelectedItem().getId());
                tags.getItems().remove(tags.getSelectionModel().getSelectedItem());
                tags.refresh();
            }
        });
        MenuItem itemAddTag = new MenuItem("Добавить");
        itemAddTag.setOnAction(event -> {
            Tag tag = TagManagerHibernateImpl.getInstance().showTagView(this.getMainStage());
            if (tag != null)
                tags.getItems().add(tag);
            tags.refresh();
        });
        MenuItem itemUpdateTag = new MenuItem("Редактировать");
        itemUpdateTag.setOnAction(event -> {
            if (tags.getSelectionModel().getSelectedItem() != null) {
                Tag tag = tags.getSelectionModel().getSelectedItem();
                TagManagerHibernateImpl.getInstance().showTagView(this.getMainStage(), tag);
                tags.refresh();
            }
        });
        MenuItem itemRefreshTags = new MenuItem("Обновить данные");
        itemRefreshTags.setOnAction(event -> tags.setItems(FXCollections.observableArrayList(tagManager.getAll())));
        contextMenuTag.getItems().addAll(itemDeleteTag, itemAddTag, itemUpdateTag, itemRefreshTags);
        tags.setContextMenu(contextMenuTag);

        departments.setItems(FXCollections.observableArrayList(departamentManager.getAll()));
        tags.setItems(FXCollections.observableArrayList(tagManager.getAll()));

    }

}
