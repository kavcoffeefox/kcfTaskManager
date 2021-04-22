package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;
import ru.kavcoffeefox.kcftaskmanager.service.impl.PersonManagerHibernateImpl;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskViewController extends AbstractController {
    private Stage dialogStage;
    private Task task;

    @FXML
    private TextField textFieldTaskName;
    @FXML
    private TextField textFieldTaskType;
    @FXML
    private TextField textEditPeriod;
    @FXML
    private DatePicker datePickerDeadline;

    @FXML
    private TextArea textAreaDescription;
    @FXML
    private ListView<Person> listPeople;
    @FXML
    private SearchableComboBox<Person> scbExecutors;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Удалить");
        item1.setOnAction(event -> {
            if (listPeople.getSelectionModel().getSelectedItem() != null)
                listPeople.getItems().remove(listPeople.getSelectionModel().getSelectedItem());
        });

        contextMenu.getItems().addAll(item1);
        this.listPeople.setContextMenu(contextMenu);

        scbExecutors.setItems(FXCollections.observableArrayList(PersonManagerHibernateImpl.getInstance().getAll()));
        scbExecutors.setMaxWidth(Double.MAX_VALUE);

        listPeople.setCellFactory(cell -> new PersonListCell());
        scbExecutors.setCellFactory(cell -> new PersonListCell());

    }

    @FXML
    private void handleAddExecutor(){
        listPeople.getItems().add(scbExecutors.getValue());
    }

    @FXML void handleNewPerson(){

    }

    @FXML
    private void handleOk(){
        if (isInputValid()){
            task.setName(textFieldTaskName.getText());
            task.setType(textFieldTaskType.getText());
            task.setPeriod(Integer.parseInt(textEditPeriod.getText()));
            task.setDescription(textAreaDescription.getText());
            task.setDeadline(datePickerDeadline.getValue());
            task.setExecutors(new ArrayList<>(listPeople.getItems()));

            dialogStage.close();
        }
    }

    @FXML
    private void handleCansel(){
        this.task = null;
        dialogStage.close();
    }


    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setTask(Task task){
        this.task = task;

        textFieldTaskName.setText(task.getName());
        datePickerDeadline.setValue(task.getDeadline());
        listPeople.setItems(FXCollections.observableArrayList(task.getExecutors()));
        textFieldTaskType.setText(task.getType());
        textEditPeriod.setText(String.valueOf(task.getPeriod()));
        textAreaDescription.setText(task.getDescription());
    }

    private boolean isInputValid(){
        String errorMessage = " ";

        return true;
    }

        class PersonListCell extends ListCell<Person> {

        @Override
        protected void updateItem(Person person, boolean b) {
            super.updateItem(person, b);
            if (b || person == null) {
                setText("");
            } else {
                setText(person.getFirstName() + person.getLastName());
            }
        }
    }
}
