package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.fxml.Initializable;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;

import java.util.ResourceBundle;

@Slf4j
public abstract class AbstractController implements Initializable {
    private ResourceBundle resourceBundle;

    // Ссылка на главное приложение.
    private Stage mainStage;
    private Stage dialogStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public Stage getMainStage() {return this.mainStage;}

    public void setItem(SimpleItem item)
    {

    }

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    public Stage getDialogStage(){ return this.dialogStage;}

    public void closeDialogView(){
        this.dialogStage.close();
    }
}
