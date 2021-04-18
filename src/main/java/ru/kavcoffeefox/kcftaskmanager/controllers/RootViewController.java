package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.Main;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;
import ru.kavcoffeefox.kcftaskmanager.service.TaskManager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TaskManagerHibernateImpl;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RootViewController extends AbstractController {

    @FXML
    private Label nowDataAndTime;

    private TaskManager taskManager;

    @FXML
    private void handleShowSettings() {
    }

    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files *.xml", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(this.getMainStage());

        if (file != null) {
            System.out.println(file);
        }
    }

    @FXML
    private void handleSave() {
        System.out.println("save");
    }

    @FXML
    private void handleSaveAs() {
        FileChooser fileChooser = new FileChooser();

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("XML files *.xml", "*.xml");
        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showSaveDialog(this.getMainStage());
        System.out.println(file);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskManager = TaskManagerHibernateImpl.getInstance();
        nowDataAndTime.setText(LocalDate.now().toString());
    }

    public void actionButtonPressed(ActionEvent actionEvent) {
        Object source = actionEvent.getSource();

        if (!(source instanceof Button)) {
            return;
        }

        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case "addtask" -> {
                Task task = new Task();
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("/view/modalwindows/TaskView.fxml"));
                    AnchorPane page = loader.load();

                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Добавление");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.initOwner(this.getMainStage());
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    TaskViewController controller = loader.getController();
                    controller.setDialogStage(dialogStage);
                    controller.setTask(task);

                    dialogStage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                taskManager.add(task);
            }
            case "edittask" -> {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("/view/modalwindows/TaskView.fxml"));
                    AnchorPane page = loader.load();

                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Редактирование");
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.initOwner(this.getMainStage());
                    Scene scene = new Scene(page);
                    dialogStage.setScene(scene);

                    TaskViewController controller = loader.getController();
                    controller.setDialogStage(dialogStage);
                    controller.setTask(taskManager.getCurrentTask());

                    dialogStage.showAndWait();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                taskManager.update(taskManager.getCurrentTask().getId(), taskManager.getCurrentTask());
            }
            case "completetask" -> {
                taskManager.getCurrentTask().setComplete(true);
                taskManager.update(taskManager.getCurrentTask().getId(), taskManager.getCurrentTask());
            }
            case "deletetask" -> taskManager.delete(taskManager.getCurrentTask().getId());
        }

    }


}
