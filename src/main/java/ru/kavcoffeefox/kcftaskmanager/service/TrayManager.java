package ru.kavcoffeefox.kcftaskmanager.service;

import com.dustinredmond.fxtrayicon.FXTrayIcon;
import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.Main;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TaskManagerHibernateImpl;

import java.net.URL;

public class TrayManager {
    private TrayManager() {}
    private Stage stage;
    private boolean isInit = false;
    private FXTrayIcon trayIcon;

    private static class LazyHolder {
        static final TrayManager INSTANCE = new TrayManager();
    }

    public static TrayManager getInstance() {
        return LazyHolder.INSTANCE;
    }
    public static TrayManager getInstance(Stage stage) {
        LazyHolder.INSTANCE.initTray(stage);
        return LazyHolder.INSTANCE;
    }

    private void initTray(Stage stage){
        this.stage = stage;
        if (FXTrayIcon.isSupported()) {
            URL iconUrl = Main.class.getResource("/images/free-icon-list-4814697.png");
            trayIcon = new FXTrayIcon(stage, iconUrl);
            trayIcon.show();

            MenuItem menuItemExit = new MenuItem("Выйти");
            menuItemExit.setOnAction(e -> {
                Platform.exit();
                System.exit(0);
            });
            MenuItem menuItemStat = new MenuItem("Статистика");
            menuItemStat.setOnAction(e -> {
                TaskManagerHibernateImpl.getInstance().showTaskStatistic();
            });
            trayIcon.addMenuItem(menuItemStat);
            trayIcon.addMenuItem(menuItemExit);
        }
    }
    public void showInfoMessage(String title, String message){
        trayIcon.showInfoMessage(title, message);
    }
    public void showInfoMessage(String message){
        trayIcon.showInfoMessage(message);
    }

    public void showSimpleMessage(String message){
        trayIcon.showMessage(message);
    }
    public void showMessage(String title, String message){
        trayIcon.showMessage(title, message);
    }


}
