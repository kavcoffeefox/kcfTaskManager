package ru.kavcoffeefox.kcftaskmanager.component;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;
import ru.kavcoffeefox.kcftaskmanager.service.TaskManager;

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
                                executors.append(person.getFirstName())
                                        .append(" ")
                                        .append(person.getLastName().toUpperCase(Locale.ROOT).charAt(1))
                                        .append(".")
                                        .append(person.getPatronymic().toUpperCase(Locale.ROOT).charAt(1))
                                        .append(".\n"));
                tooltip.setText("Исполнитель/и:\n" + executors +
                        "\nНазвание: " + tasksList.getSelectionModel().getSelectedItem().getName() +
                        "\nОписание:\n" + tasksList.getSelectionModel().getSelectedItem().getDescription());
                tooltip.setMaxSize(300, 500);
                tooltip.show(tasksList, event.getX(), event.getY());
            }
        });

        tasksList.setOnMouseExited(event -> tooltip.hide());

        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Удалить");
        MenuItem item2 = new MenuItem("Завершить");
        MenuItem item3 = new MenuItem("Продлить");
        item1.setOnAction(event -> System.out.println("Удаление задачи"));
        item2.setOnAction(event -> System.out.println("Завершение задачи"));
        item3.setOnAction(event -> System.out.println("Продление задачи на день"));
        contextMenu.getItems().addAll(item1, item2, item3);
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
