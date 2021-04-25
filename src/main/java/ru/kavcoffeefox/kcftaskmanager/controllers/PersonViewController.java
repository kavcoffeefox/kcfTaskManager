package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PersonViewController extends AbstractController{
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
            person.setFirstName(firstName.getText() == null ? "": firstName.getText());
            person.setLastName(lastName.getText() == null ? "": lastName.getText());
            person.setPatronymic(patronymic.getText() == null ? "": patronymic.getText());
            person.setBirthDay(birthDay.getValue() == null ? LocalDate.now(): birthDay.getValue());
            person.setDepartment(department.getText() == null ? "": department.getText());
            person.setPosition(position.getText() == null ? "": position.getText());
            person.setRank(rank.getText() == null ? "": rank.getText());
            person.setDescription(description.getText() == null ? "": description.getText());

            closeDialogView();
        }
    }

    @FXML
    private void handleCansel(){
        closeDialogView();
    }

    @Override
    public void setItem(SimpleItem item) {
        this.person = (Person) item;
        firstName.setText(person.getFirstName());
        lastName.setText(person.getLastName());
        patronymic.setText(person.getPatronymic());
        birthDay.setValue(person.getBirthDay());
        department.setText(person.getDepartment());
        position.setText(person.getPosition());
        rank.setText(person.getRank());
        description.setText(person.getDescription());
    }

    private boolean isInputValid(){
        String errorMessage = " ";

        return true;
    }
}
