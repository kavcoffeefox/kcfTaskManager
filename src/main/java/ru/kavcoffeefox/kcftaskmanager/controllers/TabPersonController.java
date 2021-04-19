package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabPersonController extends AbstractController{
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

    @FXML
    public void addPerson(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(RootViewController.class.getResource("/view/modalwindows/PersonView.fxml"));
            AnchorPane page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Редактирование");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(this.getMainStage());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            PersonViewController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
