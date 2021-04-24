package ru.kavcoffeefox.kcftaskmanager.service.impl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.controllers.AbstractController;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;

import java.io.IOException;

public abstract class AbstractManager{

    abstract protected void showView(Stage stage, SimpleItem item);

    protected void showModalView(Stage stage, String resource, SimpleItem item){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AbstractController.class.getResource(resource));
            AnchorPane page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AbstractController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setItem(item);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
