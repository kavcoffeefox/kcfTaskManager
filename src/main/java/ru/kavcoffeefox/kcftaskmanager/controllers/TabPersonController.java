package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.PersonManagerHibernateImpl;

import java.io.IOException;
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
    public void setMainStage(Stage mainStage) {
        super.setMainStage(mainStage);
    }

    @Override
    public Stage getMainStage() {
        return super.getMainStage();
    }

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
//        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        personTableView.setItems(FXCollections.observableArrayList(personManager.getAll()));

        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemDelete = new MenuItem("Удалить");
        itemDelete.setOnAction(event -> {
            if (personTableView.getSelectionModel().getSelectedItem() != null) {
                personManager.delete(personTableView.getSelectionModel().getSelectedItem().getId());
                personTableView.getItems().remove(personTableView.getSelectionModel().getSelectedItem());
            }
        });
        MenuItem itemAdd = new MenuItem("Добавить");
        itemAdd.setOnAction(event -> {
            this.addPerson();
        });
        MenuItem itemUpdate = new MenuItem("Редактировать");
        itemUpdate.setOnAction(event -> {
            if (personTableView.getSelectionModel().getSelectedItem() != null) {
                Person person = personTableView.getSelectionModel().getSelectedItem();
                System.out.println(person);
                showPersonView(person);
                personManager.update(person.getId(), person);
            }
        });

        contextMenu.getItems().addAll(itemDelete);
        contextMenu.getItems().addAll(itemAdd);
        contextMenu.getItems().addAll(itemUpdate);
        this.personTableView.setContextMenu(contextMenu);
    }

    public void showPersonView(Person person) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TabPersonController.class.getResource("/view/modalwindows/PersonView.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Сотрудник");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.getMainStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addPerson() {
        Person person = new Person();
        showPersonView(person);
        personManager.add(person);
    }
}
