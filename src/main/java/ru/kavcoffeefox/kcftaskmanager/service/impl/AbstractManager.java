package ru.kavcoffeefox.kcftaskmanager.service.impl;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.controller.AbstractController;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;

import java.io.IOException;

public abstract class AbstractManager{

    abstract protected boolean showView(Stage stage, SimpleItem item);

    protected boolean showModalView(Stage stage, String resource, SimpleItem item){
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
            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
