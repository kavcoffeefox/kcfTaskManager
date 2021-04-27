package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;
import ru.kavcoffeefox.kcftaskmanager.service.TaskManager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TaskManagerHibernateImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class TabTableController extends AbstractController {
    private final Tooltip tooltip;

    @FXML
    private TableView<Task> taskTableView;
    @FXML
    private TableColumn<Task, String> taskDateColumn;
    @FXML
    private TableColumn<Task, String> taskNameColumn;
    @FXML
    private TableColumn<Task, String> taskWorkerColumn;
    @FXML
    private TableColumn<Task, String> taskDescriptionColumn;


    private final TaskManager taskManager = TaskManagerHibernateImpl.getInstance();

    @Override
    public void setMainStage(Stage mainStage) {
        super.setMainStage(mainStage);
    }

    @Override
    public Stage getMainStage() {
        return super.getMainStage();
    }

    public TabTableController() {
        tooltip = new Tooltip(null);
        tooltip.setWrapText(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeadline().toString()));
        taskNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        taskWorkerColumn.setCellValueFactory(cellData -> {
            StringBuilder sb = new StringBuilder();
            for (Person person : cellData.getValue().getExecutors()) {
                sb.append(Person.getFIO(person)).append("\n");
            }
            return new SimpleStringProperty(sb.toString());
        });
        taskDescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        taskDescriptionColumn.setCellFactory(param -> {
            TableCell<Task, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.textProperty().bind(cell.itemProperty());
            text.wrappingWidthProperty().bind(taskDescriptionColumn.widthProperty());
            return cell;
        });
        taskNameColumn.setCellFactory(param -> {
            TableCell<Task, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            text.textProperty().bind(cell.itemProperty());
            text.wrappingWidthProperty().bind(taskNameColumn.widthProperty());
            return cell;
        });

        taskTableView.setRowFactory((TableView<Task> tv) -> new TableRow<>() {
            @Override
            protected void updateItem(Task item, boolean empty) {

                taskTableView.setOnMousePressed(event -> {

                    if (taskTableView.getSelectionModel().getSelectedItem() != null) {
                        taskManager.setCurrentTask(taskTableView.getSelectionModel().getSelectedItem());
                        StringBuilder executors = new StringBuilder();
                        taskTableView.getSelectionModel().getSelectedItem().getExecutors()
                                .forEach(person ->
                                        executors.append(Person.getFIO(person)).append("\n"));
                        tooltip.setText("Исполнитель/и:\n" + executors +
                                "\nНазвание: " + taskTableView.getSelectionModel().getSelectedItem().getName() +
                                "\nОписание:\n" + taskTableView.getSelectionModel().getSelectedItem().getDescription());
                        tooltip.setMaxSize(300, 500);
                        tooltip.show(taskTableView, event.getX(), event.getY());
                    }
                });

                taskTableView.setOnMouseExited(event -> tooltip.hide());
            }
        });

        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemDelete = new MenuItem("Удалить");
        itemDelete.setOnAction(event -> {
            if (taskTableView.getSelectionModel().getSelectedItem() != null) {
                taskManager.delete(taskTableView.getSelectionModel().getSelectedItem().getId());
                taskTableView.getItems().remove(taskTableView.getSelectionModel().getSelectedItem());
                taskTableView.refresh();
            }
        });
        MenuItem itemAdd = new MenuItem("Добавить");
        itemAdd.setOnAction(event -> {
            Task task = TaskManagerHibernateImpl.getInstance().showTaskView(this.getMainStage());
            if (task != null)
                taskTableView.getItems().add(task);
            taskTableView.refresh();
        });
        MenuItem itemUpdate = new MenuItem("Редактировать");
        itemUpdate.setOnAction(event -> {
            if (taskTableView.getSelectionModel().getSelectedItem() != null) {
                Task task = taskTableView.getSelectionModel().getSelectedItem();
                TaskManagerHibernateImpl.getInstance().showTaskView(this.getMainStage(), task);
                taskManager.update(task.getId(), task);
                taskTableView.refresh();
            }
        });
        MenuItem itemComplete = new MenuItem("Завершить");
        itemComplete.setOnAction(event -> {
            if (taskTableView.getSelectionModel().getSelectedItem() != null) {
                taskTableView.getSelectionModel().getSelectedItem().setComplete(true);
                taskManager.update(taskTableView.getSelectionModel().getSelectedItem().getId(), taskTableView.getSelectionModel().getSelectedItem());
            }
        });
        MenuItem itemRefresh = new MenuItem("Обновить данные");
        itemRefresh.setOnAction(event -> {
            taskTableView.setItems(FXCollections.observableArrayList(taskManager.getAll()));
        });

        contextMenu.getItems().addAll(itemDelete, itemAdd, itemUpdate, itemComplete, itemRefresh);
        taskTableView.setContextMenu(contextMenu);

        taskTableView.setOnMousePressed(event -> taskManager.setCurrentTask(taskTableView.getSelectionModel().getSelectedItem()));
        taskTableView.setItems(FXCollections.observableArrayList(taskManager.getAll()));
    }

}
