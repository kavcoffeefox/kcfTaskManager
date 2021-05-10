package ru.kavcoffeefox.kcftaskmanager.controller.tab_controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import java.awt.Desktop;

import ru.kavcoffeefox.kcftaskmanager.controller.AbstractController;
import ru.kavcoffeefox.kcftaskmanager.entity.Document;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.DocumentManagerHibernateImpl;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class TabDocumentViewController extends AbstractController {
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
        personColumn.setCellValueFactory(cellData -> {
            StringBuilder sb = new StringBuilder();
            for (Person person : cellData.getValue().getPersons()) {
                sb.append(Person.getFIO(person)).append("\n");
            }
            return new SimpleStringProperty(sb.toString());
        });

        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Document, String> call(final TableColumn<Document, String> param) {
                final TableCell<Document, String> cell = new TableCell<>() {

                    final Button btn = new Button("Открыть папку");


                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(actionEvent -> {
                                if (Desktop.isDesktopSupported()) {
                                    if ( documentTable.getSelectionModel().getSelectedItem() != null)
                                        Desktop.getDesktop().browseFileDirectory(new File(documentTable.getSelectionModel().getSelectedItem().getPath()));
                                } else {
                                    System.out.println("None sup");
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });


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
                documentTable.refresh();
            }
        });
        MenuItem itemRefresh = new MenuItem("Обновить данные");
        itemRefresh.setOnAction(event -> documentTable.setItems(FXCollections.observableArrayList(documentManager.getAll())));

        contextMenu.getItems().addAll(itemDelete, itemAdd, itemUpdate, itemRefresh);
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
