package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.*;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class PersonViewController extends AbstractController{
    private Stage dialogStage;
    private Person person;

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField patronymic;
    @FXML
    private DatePicker birthDay;
    @FXML
    private TextField department;
    @FXML
    private TextField position;
    @FXML
    private TextField rank;
    @FXML
    private TextArea description;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void handleSave(){
        if (isInputValid()){
            person.setFirstName(firstName.getText());
            person.setLastName(lastName.getText());
            person.setPatronymic(patronymic.getText());
            person.setBirthDay(birthDay.getValue());
            person.setDepartment(department.getText());
            person.setPosition(position.getText());
            person.setRank(rank.getText());

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

    public void setPerson(Person person){
        this.person = person;
    }

    private boolean isInputValid(){
        String errorMessage = " ";

        return true;
    }
}
