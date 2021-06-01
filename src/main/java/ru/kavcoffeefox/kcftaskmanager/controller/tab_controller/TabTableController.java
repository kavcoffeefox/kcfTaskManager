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
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;
import ru.kavcoffeefox.kcftaskmanager.service.TaskManager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TaskManagerHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.utils.ItemUtil;

import java.net.URL;
import java.util.ResourceBundle;

public class TabTableController extends AbstractController {
    private final Tooltip  tooltip = new Tooltip(null);

    @FXML
    private TextField searchField;
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

    private ObservableList<Task> taskList;
    private FilteredList<Task> filteredData;

    private final TaskManager taskManager = TaskManagerHibernateImpl.getInstance();


    public TabTableController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tooltip.setWrapText(true);
        taskDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDeadline() != null ?cellData.getValue().getDeadline().toString(): ""));
        taskNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        taskWorkerColumn.setCellValueFactory(cellData -> {
            StringBuilder sb = new StringBuilder();
            for (Person person : cellData.getValue().getExecutors()) {
                sb.append(Person.getFIO(person)).append("\n");
            }
            return new SimpleStringProperty(sb.toString());
        });
        taskDescriptionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));

        taskTableView.setRowFactory((TableView<Task> tv) -> new TableRow<>() {
            @Override
            protected void updateItem(Task item, boolean empty) {

                setOnMousePressed(event -> {

                    if (taskTableView.getSelectionModel().getSelectedItem() != null) {
                        StringBuilder executors = new StringBuilder();
                        taskTableView.getSelectionModel().getSelectedItem().getExecutors()
                                .forEach(person ->
                                        executors.append(Person.getFIO(person)).append("\n"));

                        tooltip.setText("Исполнитель/и:\n" + executors +
                                "\nНазвание: " + taskTableView.getSelectionModel().getSelectedItem().getName() +
                                "\nОписание:\n" + taskTableView.getSelectionModel().getSelectedItem().getDescription() +
                                "\n\nТеги: " + ItemUtil.tagInOneLine(taskTableView.getSelectionModel().getSelectedItem()));
                        tooltip.setMaxSize(300, 500);
                        tooltip.show(taskTableView, event.getX(), event.getY());
                    }
                });

                taskTableView.setOnMouseExited(event -> tooltip.hide());
                taskTableView.refresh();
            }
        });

        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemDelete = new MenuItem("Удалить");
        itemDelete.setOnAction(event -> {
            if (taskTableView.getSelectionModel().getSelectedItem() != null) {
                taskManager.delete(taskTableView.getSelectionModel().getSelectedItem().getId());
                taskList.remove(taskTableView.getSelectionModel().getSelectedItem());
                taskTableView.refresh();
            }
        });
        MenuItem itemAdd = new MenuItem("Добавить");
        itemAdd.setOnAction(event -> {
            Task task = TaskManagerHibernateImpl.getInstance().showTaskView(this.getMainStage());
            if (task != null) {
                setItems();
            }
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
                if (!taskTableView.getSelectionModel().getSelectedItem().isComplete()) {
                    taskTableView.getSelectionModel().getSelectedItem().setComplete(true);
                    taskManager.complete(taskTableView.getSelectionModel().getSelectedItem().getId());
                    setItems();
                }
            }
        });
        MenuItem itemRefresh = new MenuItem("Обновить данные");
        itemRefresh.setOnAction(event -> setItems());

        contextMenu.getItems().addAll(itemDelete, itemAdd, itemUpdate, itemComplete, itemRefresh);
        taskTableView.setContextMenu(contextMenu);
        setItems();

        taskTableView.setRowFactory(new Callback<TableView<Task>, TableRow<Task>>() {
            @Override
            public TableRow<Task> call(TableView<Task> tableView) {
                final TableRow<Task> row = new TableRow<Task>() {
                    @Override
                    protected void updateItem(Task task, boolean empty){
                        super.updateItem(task, empty);
                        if (task != null && task.isComplete()) {
                            getStyleClass().clear();
                            getStyleClass().add("isComplete");
                        } else  if (task != null && !task.isComplete()){
                            getStyleClass().clear();
                            getStyleClass().add("notComplete");
                        }
                        else {
                            getStyleClass().clear();
                        }
                    }
                };

                return row;
            }
        });
        TaskManagerHibernateImpl.getInstance().showTaskStatistic();
    }

    private void setItems(){
        taskList = FXCollections.observableArrayList(taskManager.getAll());
        filteredData = new FilteredList<>(taskList, p -> true);
        SortedList<Task> sortedData = new SortedList<>(filteredData);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(task -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();
            if (task.getName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (task.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return ItemUtil.tagInOneLine(task).toLowerCase().contains(lowerCaseFilter);
        }));
        sortedData.comparatorProperty().bind(taskTableView.comparatorProperty());

        taskTableView.setItems(sortedData);
    }

}
