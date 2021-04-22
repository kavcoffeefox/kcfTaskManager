package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.entity.Document;

import java.net.URL;
import java.util.ResourceBundle;

public class TabDocumentViewController extends AbstractController{

    @FXML
    public TableView<Document> documentTable;
    @FXML
    public TableColumn<Document, String> dateColumn;
    @FXML
    public TableColumn<Document, String> nameColumn;
    @FXML
    public TableColumn<Document, String> requisiteColumn;
    @FXML
    public TableColumn<Document, String> descriptionColumn;
    @FXML
    public TableColumn<Document, String> personColumn;
    @FXML
    public TableColumn<Document, String> actionColumn;

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
}
