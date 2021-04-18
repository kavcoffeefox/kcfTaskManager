package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

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
    private TextField textFieldPeople;
    @FXML
    private DatePicker datePickerDeadline;

    @FXML
    private TextArea textAreaDescription;
    @FXML
    private ListView<String> listPeople;



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
    }

    @FXML
    private void handleAddWorker(){

    }

    @FXML
    private void handleOk(){
        if (isInputValid()){
            task.setName(textFieldTaskName.getText());
            task.setType(textFieldTaskType.getText());
            task.setPeriod(Integer.parseInt(textEditPeriod.getText()));
            task.setDescription(textAreaDescription.getText());
            task.setDeadline(datePickerDeadline.getValue());
//            task.setExecutors(listPeople.getItems().stream().collect(Collectors.toList()));

            dialogStage.close();
        }
    }

    @FXML
    private void handleCansel(){
        dialogStage.close();
    }


    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }

    public void setTask(Task task){
        this.task = task;

        textFieldTaskName.setText(task.getName());
        datePickerDeadline.setValue(task.getDeadline());
        listPeople.setItems(FXCollections.observableArrayList(task.getExecutors().stream().map(person -> person.getFirstName()
                + " "
                + person.getLastName().toUpperCase(Locale.ROOT).charAt(1)
                + "."
                + person.getPatronymic().toUpperCase(Locale.ROOT).charAt(1)
                +(".\n")).collect(Collectors.toList())));
        textFieldTaskType.setText(task.getType());
        textEditPeriod.setText(String.valueOf(task.getPeriod()));
        textAreaDescription.setText(task.getDescription());
    }

    private boolean isInputValid(){
        String errorMessage = " ";

        return true;
    }
}
