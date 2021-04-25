package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RootViewController extends AbstractController {

    @FXML
    private Label nowDataAndTime;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nowDataAndTime.setText(LocalDate.now().toString());
    }


}
