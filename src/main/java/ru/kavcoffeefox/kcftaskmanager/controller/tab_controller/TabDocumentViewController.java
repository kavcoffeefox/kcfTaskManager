package ru.kavcoffeefox.kcftaskmanager.controller.tab_controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import ru.kavcoffeefox.kcftaskmanager.controller.AbstractController;
import ru.kavcoffeefox.kcftaskmanager.entity.Document;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.DocumentManagerHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.utils.ItemUtil;

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

    @FXML
    public TextField searchField;
    private ObservableList<Document> documentList;
    private FilteredList<Document> filteredData;

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
                documentList.remove(documentTable.getSelectionModel().getSelectedItem());
                documentTable.refresh();
            }
        });
        MenuItem itemAdd = new MenuItem("Добавить");
        itemAdd.setOnAction(event -> {
            Document document = DocumentManagerHibernateImpl.getInstance().showDocumentView(this.getMainStage());
            if (document != null)
                setItems();
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
        itemRefresh.setOnAction(event -> setItems());

        contextMenu.getItems().addAll(itemDelete, itemAdd, itemUpdate, itemRefresh);
        documentTable.setContextMenu(contextMenu);
        setItems();
    }

    private void setItems(){
        documentList=FXCollections.observableArrayList(documentManager.getAll());
        filteredData = new FilteredList<>(documentList, p -> true);
        SortedList<Document> sortedData = new SortedList<>(filteredData);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(document -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();
            if (document.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (document.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return ItemUtil.tagInOneLine(document).toLowerCase().contains(lowerCaseFilter);
        }));
        sortedData.comparatorProperty().bind(documentTable.comparatorProperty());

        documentTable.setItems(sortedData);
    }

    @FXML
    public void addDocument(){
        Document document = DocumentManagerHibernateImpl.getInstance().showDocumentView(this.getMainStage());
        if (document != null)
            documentTable.getItems().add(document);
        documentTable.refresh();
    }

}
