package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PersonViewController extends AbstractController{
    private Stage dialogStage;

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

    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
}
