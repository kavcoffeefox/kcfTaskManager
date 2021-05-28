package ru.kavcoffeefox.kcftaskmanager.controller.tab_controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import ru.kavcoffeefox.kcftaskmanager.component.SimpleDay;
import ru.kavcoffeefox.kcftaskmanager.controller.AbstractController;
import ru.kavcoffeefox.kcftaskmanager.service.TaskManager;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TaskManagerHibernateImpl;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TabWeeksController extends AbstractController {
    private LocalDate startDay;
    private LocalDate currentDay;
    @FXML
    private GridPane gridPane;
    private TaskManager taskManager = TaskManagerHibernateImpl.getInstance();

    private ObservableList<SimpleDay> listSimpleDays = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startDay = getFirstDayOfTheWeek(currentDay);
        initDays(startDay);

    }

    public TabWeeksController() {
        currentDay = LocalDate.now();
    }

    @FXML
    private void handleLeftBias() {
        startDay = startDay.minusWeeks(1);
        updateDaysDate();
    }

    @FXML
    private void handleRightBias() {
        startDay = startDay.plusWeeks(1);
        updateDaysDate();

    }

    @FXML
    private void handleRefresh() {
        showData();
    }

    private void initDays(LocalDate tempLocalDate) {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 7; j++) {
                SimpleDay simpleDay = new SimpleDay(tempLocalDate.plusDays((i * 7) + j), taskManager);
                simpleDay.setMainStage(this.getMainStage());
                listSimpleDays.add(simpleDay);
                gridPane.add(simpleDay, j, i);
            }
    }

    private void updateDaysDate() {
        for (int i = 0; i < 28; i++) {
            listSimpleDays.get(i).setDate(startDay.plusDays(i));
        }
    }
    private void refreshDays() {
        for (int i = 0; i < 28; i++) {
            listSimpleDays.get(i).refresh();
        }
    }
    public void showData() {
        refreshDays();
    }

    private LocalDate getFirstDayOfTheWeek(LocalDate localDate) {
        if (localDate.getDayOfWeek() == DayOfWeek.MONDAY) return localDate;
        if (localDate.getDayOfWeek() == DayOfWeek.TUESDAY) return localDate.minusDays(1);
        if (localDate.getDayOfWeek() == DayOfWeek.WEDNESDAY) return localDate.minusDays(2);
        if (localDate.getDayOfWeek() == DayOfWeek.THURSDAY) return localDate.minusDays(3);
        if (localDate.getDayOfWeek() == DayOfWeek.FRIDAY) return localDate.minusDays(4);
        if (localDate.getDayOfWeek() == DayOfWeek.SATURDAY) return localDate.minusDays(5);
        if (localDate.getDayOfWeek() == DayOfWeek.SUNDAY) return localDate.minusDays(6);
        return localDate;
    }
}
