package ru.kavcoffeefox.kcftaskmanager.component;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;
import ru.kavcoffeefox.kcftaskmanager.service.TaskManager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TaskManagerHibernateImpl;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Objects;

public class SimpleDay extends BorderPane {

    private Stage mainStage;
    private final ListView<Task> tasksList = new ListView<>();
    private final Label lblDayData = new Label();
    private final Tooltip tooltip = new Tooltip(null);
    private LocalDate date;
    private final TaskManager taskManager;

    public SimpleDay(LocalDate localDate, TaskManager tm) {
        taskManager = tm;
        tooltip.setWrapText(true);
        date = localDate;
        this.setDate(Objects.requireNonNullElseGet(localDate, LocalDate::now));
        this.setTop(lblDayData);
        this.setCenter(tasksList);

        tasksList.setStyle("-fx-background-color: #FFFAFA;" +
                "-fx-border-color: black;");
        tasksList.setOnMousePressed(event -> {
            if (tasksList.getSelectionModel().getSelectedItem() != null) {
                StringBuilder executors = new StringBuilder();
                tasksList.getSelectionModel().getSelectedItem().getExecutors()
                        .forEach(person ->
                                executors.append(Person.getFIO(person)).append("\n"));
                tooltip.setText("Исполнитель/и:\n" + executors +
                        "\nНазвание: " + tasksList.getSelectionModel().getSelectedItem().getName() +
                        "\nОписание:\n" + tasksList.getSelectionModel().getSelectedItem().getDescription());
                tooltip.setMaxSize(300, 500);
                tooltip.show(tasksList, event.getX(), event.getY());
            }
        });

        tasksList.setCellFactory(cell -> new ListCell<>() {
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);

                if (empty || task == null) {
                    setText("");
                    getStyleClass().clear();
                } else {
                    setText(task.getName());
                    if (task.isComplete()) {
                        getStyleClass().add("isComplete");
                    }
                    else {
                        getStyleClass().add("notComplete");
                    }
                }
            }
        });

        tasksList.setOnMouseExited(event -> tooltip.hide());
        tasksList.focusedProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue) {
                tasksList.getSelectionModel().clearSelection();
            }
        });
        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemDelete = new MenuItem("Удалить");
        itemDelete.setOnAction(event -> {
            if (tasksList.getSelectionModel().getSelectedItem() != null) {
                taskManager.delete(tasksList.getSelectionModel().getSelectedItem().getId());
                tasksList.getItems().remove(tasksList.getSelectionModel().getSelectedItem());
                refresh();
            }
        });
        MenuItem itemAdd = new MenuItem("Добавить");
        itemAdd.setOnAction(event -> {
            Task task = TaskManagerHibernateImpl.getInstance().showTaskView(this.getMainStage());
            if (task != null)
                tasksList.getItems().add(task);
            refresh();
        });
        MenuItem itemUpdate = new MenuItem("Редактировать");
        itemUpdate.setOnAction(event -> {
            if (tasksList.getSelectionModel().getSelectedItem() != null) {
                Task task = tasksList.getSelectionModel().getSelectedItem();
                TaskManagerHibernateImpl.getInstance().showTaskView(this.getMainStage(), task);
                taskManager.update(task.getId(), task);
                tasksList.refresh();
            }
        });
        MenuItem itemComplete = new MenuItem("Завершить");
        itemComplete.setOnAction(event -> {
            if (tasksList.getSelectionModel().getSelectedItem() != null) {
                if (!tasksList.getSelectionModel().getSelectedItem().isComplete()) {
                    tasksList.getSelectionModel().getSelectedItem().setComplete(true);
                    taskManager.complete(tasksList.getSelectionModel().getSelectedItem().getId());
                    refresh();
                }
            }
        });

        contextMenu.getItems().addAll(itemDelete, itemAdd, itemUpdate, itemComplete);
        tasksList.setContextMenu(contextMenu);
    }

    public void setDate(LocalDate localDate) {
        date = localDate;
        lblDayData.setText(localDate.toString());
        tasksList.setItems(FXCollections.observableArrayList(taskManager.tasks(date)));
        if (localDate.equals(LocalDate.now())) {
            this.setStyle("-fx-background-color: #00cc00"); //#6699ff
            this.lblDayData.setText("Сегодня");
        } else {
            if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY)
                this.setStyle("-fx-background-color: #ffcc33");
            else if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY)
                this.setStyle("-fx-background-color: #ff0033");
            else this.setStyle("-fx-background-color: #99ffff");
        }
    }
    public void refresh(){
        tasksList.setItems(FXCollections.observableArrayList(taskManager.tasks(date)));
        tasksList.refresh();
    }
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    public Stage getMainStage() {return this.mainStage;}
}
