package ru.kavcoffeefox.kcftaskmanager;

import ru.kavcoffeefox.kcftaskmanager.controllers.TabDocumentViewController;
import ru.kavcoffeefox.kcftaskmanager.controllers.TabPersonController;
import ru.kavcoffeefox.kcftaskmanager.controllers.TabTableController;
import ru.kavcoffeefox.kcftaskmanager.controllers.TabWeeksController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;

import java.io.IOException;
import java.time.LocalDateTime;

public class Main extends Application {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private Stage primaryStage;
    private BorderPane rootLayout;


    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Task manager");
        initRootLayout();
        showMainTabsView();

    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/RootView.fxml"));
            this.rootLayout = loader.load();
            Scene scene = new Scene(this.rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
            logger.info("init Root Layout -> Success!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMainTabsView() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/MainTabsView.fxml"));
            AnchorPane mainTabsView = loader.load();

            rootLayout.setCenter(mainTabsView);
            BorderPane.setMargin(mainTabsView, new Insets(0, 0, 0, 0));
            logger.info("Main tabs view showed!");
            showTabWorkViewTabWeek();
            showTabWorkTableTab();
            showTabPersonViewTab();
            showTabDocumentViewTab();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTabWorkViewTabWeek() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/tabs/TabWeekView.fxml"));
            AnchorPane workViewTabWeek = loader.load();

            TabPane tabPane = (TabPane) ((AnchorPane) rootLayout.getCenter()).getChildren().get(0);
            tabPane.getTabs().get(1).setContent(workViewTabWeek);
            BorderPane.setMargin(workViewTabWeek, new Insets(0, 0, 0, 0));
            TabWeeksController tabWeeksController = loader.getController();
            tabWeeksController.setMainStage(this.primaryStage);
            logger.info("Tab with weeks view is showed!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTabWorkTableTab() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/tabs/TabTableView.fxml"));
            AnchorPane tabTableView = loader.load();

            TabPane tabPane = (TabPane) ((AnchorPane) rootLayout.getCenter()).getChildren().get(0);
            tabPane.getTabs().get(0).setContent(tabTableView);
            BorderPane.setMargin(tabTableView, new Insets(0, 0, 0, 0));
            TabTableController tabTableController = loader.getController();
            tabTableController.setMainStage(this.primaryStage);
            logger.info("Tab with table task view is showed!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTabPersonViewTab() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/tabs/TabPersonView.fxml"));
            BorderPane tabTableView = loader.load();

            TabPane tabPane = (TabPane) ((AnchorPane) rootLayout.getCenter()).getChildren().get(0);
            tabPane.getTabs().get(3).setContent(tabTableView);
            BorderPane.setMargin(tabTableView, new Insets(0, 0, 0, 0));
            TabPersonController tabPersonController = loader.getController();
            tabPersonController.setMainStage(this.primaryStage);
            logger.info("Tab with person view is showed!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTabDocumentViewTab() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/view/tabs/TabDocumentView.fxml"));
            BorderPane tabTableView = loader.load();

            TabPane tabPane = (TabPane) ((AnchorPane) rootLayout.getCenter()).getChildren().get(0);
            tabPane.getTabs().get(2).setContent(tabTableView);
            BorderPane.setMargin(tabTableView, new Insets(0, 0, 0, 0));
            TabDocumentViewController tabDocumentViewController = loader.getController();
            tabDocumentViewController.setMainStage(this.primaryStage);
            logger.info("Tab with document view is showed!!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public static void main(String[] args) {
        logger.info("App start! Time: {}", LocalDateTime.now());
        launch(args);
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }
}

