package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ru.kavcoffeefox.kcftaskmanager.entity.Document;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.DocumentManagerHibernateImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class TabDocumentViewController extends AbstractController{
    Manager<Document, Integer> documentManager = DocumentManagerHibernateImpl.getInstance();
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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate() == null ? new SimpleStringProperty(" "): new SimpleStringProperty(cellData.getValue().getDate().toString()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        requisiteColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRequisite()));
        descriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemDelete = new MenuItem("Удалить");
        itemDelete.setOnAction(event -> {
            if (documentTable.getSelectionModel().getSelectedItem() != null) {
                documentManager.delete(documentTable.getSelectionModel().getSelectedItem().getId());
                documentTable.getItems().remove(documentTable.getSelectionModel().getSelectedItem());
                documentTable.refresh();
            }
        });
        MenuItem itemAdd = new MenuItem("Добавить");
        itemAdd.setOnAction(event -> {
            Document document = DocumentManagerHibernateImpl.getInstance().showDocumentView(this.getMainStage());
            if (document != null)
                documentTable.getItems().add(document);
            documentTable.refresh();
        });
        MenuItem itemUpdate = new MenuItem("Редактировать");
        itemUpdate.setOnAction(event -> {
            if (documentTable.getSelectionModel().getSelectedItem() != null) {
                Document document = documentTable.getSelectionModel().getSelectedItem();
                DocumentManagerHibernateImpl.getInstance().showDocumentView(this.getMainStage(), document);
                documentManager.update(document.getId(), document);
                documentTable.refresh();
            }
        });

        contextMenu.getItems().addAll(itemDelete, itemAdd, itemUpdate);
        documentTable.setContextMenu(contextMenu);
        documentTable.getItems().addAll(documentManager.getAll());
    }

    @FXML
    public void addDocument(){
        Document document = DocumentManagerHibernateImpl.getInstance().showDocumentView(this.getMainStage());
        if (document != null)
            documentTable.getItems().add(document);
        documentTable.refresh();
    }

}
