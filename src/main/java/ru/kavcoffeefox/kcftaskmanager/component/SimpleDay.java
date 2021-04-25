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
import java.util.Locale;
import java.util.Objects;

public class SimpleDay extends BorderPane {
    private ListView<Task> tasksList = new ListView<>();
    private Label lblDayData = new Label();
    private Tooltip tooltip = new Tooltip(null);
    private LocalDate date;
    private TaskManager taskManager;

    public SimpleDay(LocalDate localDate, TaskManager tm) {
        taskManager = tm;
        tooltip.setWrapText(true);
        date = localDate;
        this.setDate(Objects.requireNonNullElseGet(localDate, LocalDate::now));
        this.setTop(lblDayData);
        this.setCenter(tasksList);

        tasksList.setOnMousePressed(event -> {
            if (tasksList.getSelectionModel().getSelectedItem() != null) {
                taskManager.setCurrentTask(tasksList.getSelectionModel().getSelectedItem());
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
            protected void updateItem(Task task, boolean b) {
                super.updateItem(task, b);

                if (b || task == null) {
                    setText(" ");
                } else {
                    setText(task.getName());
                }
            }
        });

        tasksList.setOnMouseExited(event -> tooltip.hide());

        ContextMenu contextMenu = new ContextMenu();

        MenuItem itemDelete = new MenuItem("Удалить");
        itemDelete.setOnAction(event -> {
            if (tasksList.getSelectionModel().getSelectedItem() != null) {
                taskManager.delete(tasksList.getSelectionModel().getSelectedItem().getId());
                tasksList.getItems().remove(tasksList.getSelectionModel().getSelectedItem());
                tasksList.refresh();
            }
        });
        MenuItem itemAdd = new MenuItem("Добавить");
        itemAdd.setOnAction(event -> {
            Task task = TaskManagerHibernateImpl.getInstance().showTaskView(new Stage());
            if (task != null)
                tasksList.getItems().add(task);
            tasksList.refresh();
        });
        MenuItem itemUpdate = new MenuItem("Редактировать");
        itemUpdate.setOnAction(event -> {
            if (tasksList.getSelectionModel().getSelectedItem() != null) {
                Task task = tasksList.getSelectionModel().getSelectedItem();
                TaskManagerHibernateImpl.getInstance().showTaskView(new Stage(), task);
                taskManager.update(task.getId(), task);
                tasksList.refresh();
            }
        });
        MenuItem itemComplete = new MenuItem("Завершить");
        itemComplete.setOnAction(event -> {
            if (taskManager.getCurrentTask() != null) {
                taskManager.getCurrentTask().setComplete(true);
                taskManager.update(taskManager.getCurrentTask().getId(), taskManager.getCurrentTask());
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
}
