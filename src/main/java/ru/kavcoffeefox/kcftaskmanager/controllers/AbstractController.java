package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public abstract class AbstractController implements Initializable {
    private ResourceBundle resourceBundle;

    // Ссылка на главное приложение.
    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public Stage getMainStage() {return this.mainStage;}



}
