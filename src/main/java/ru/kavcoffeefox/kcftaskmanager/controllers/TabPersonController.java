package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.PersonManagerHibernateImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class TabPersonController extends AbstractController {
    Manager<Person, Integer> personManager;

    @FXML
    private TableView<Person> personTableView;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;
    @FXML
    private TableColumn<Person, String> patronymicColumn;
    @FXML
    private TableColumn<Person, String> birthDayColumn;
    @FXML
    private TableColumn<Person, String> departmentColumn;
    @FXML
    private TableColumn<Person, String> positionColumn;
    @FXML
    private TableColumn<Person, String> rankColumn;
    @FXML
    private TableColumn<Person, String> descriptionColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        personManager = PersonManagerHibernateImpl.getInstance();

        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        patronymicColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatronymic()));
        birthDayColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDay().toString()));
        departmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartment()));
        positionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));
        rankColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRank()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        personTableView.setItems(FXCollections.observableArrayList(personManager.getAll()));

        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemDelete = new MenuItem("Удалить");
        itemDelete.setOnAction(event -> {
            if (personTableView.getSelectionModel().getSelectedItem() != null) {
                personManager.delete(personTableView.getSelectionModel().getSelectedItem().getId());
                personTableView.getItems().remove(personTableView.getSelectionModel().getSelectedItem());
                personTableView.refresh();
            }
        });
        MenuItem itemAdd = new MenuItem("Добавить");
        itemAdd.setOnAction(event -> {
            Person person = PersonManagerHibernateImpl.getInstance().showPersonView(this.getMainStage());
            if (person != null)
                personTableView.getItems().add(person);
            personTableView.refresh();
        });
        MenuItem itemUpdate = new MenuItem("Редактировать");
        itemUpdate.setOnAction(event -> {
            if (personTableView.getSelectionModel().getSelectedItem() != null) {
                Person person = personTableView.getSelectionModel().getSelectedItem();
                PersonManagerHibernateImpl.getInstance().showPersonView(this.getMainStage(),person);
                personManager.update(person.getId(), person);
                personTableView.refresh();
            }
        });

        contextMenu.getItems().addAll(itemDelete, itemAdd, itemUpdate);
        this.personTableView.setContextMenu(contextMenu);
    }

    @FXML
    public void addPerson() {
        Person person = new Person();
        PersonManagerHibernateImpl.getInstance().showPersonView(this.getMainStage() ,person);
        personManager.add(person);
        personTableView.getItems().add(person);
        personTableView.refresh();
    }
}
