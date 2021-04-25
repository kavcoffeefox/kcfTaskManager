package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.SearchableComboBox;
import ru.kavcoffeefox.kcftaskmanager.entity.*;
import ru.kavcoffeefox.kcftaskmanager.service.impl.PersonManagerHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TagManagerHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TaskManagerHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.utils.PersonListCell;
import ru.kavcoffeefox.kcftaskmanager.utils.TagListCell;
import ru.kavcoffeefox.kcftaskmanager.utils.TaskListCell;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

@Slf4j
public class DocumentViewController extends AbstractController{
    private Document document;

    @FXML
    public TextField textFieldName;
    @FXML
    public TextField textFieldRequisite;
    @FXML
    public TextArea textAreaDescription;
    @FXML
    public DatePicker datePickerDate;
    @FXML
    public SearchableComboBox<Person> scbPerson;
    @FXML
    public SearchableComboBox<Task> scbTask;
    @FXML
    public SearchableComboBox<Tag> scbTag;
    @FXML
    public ListView<Person> listPersons;
    @FXML
    public ListView<Task> listTasks;
    @FXML
    public ListView<Tag> listTags;
    @FXML
    public TextField textFieldPath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        scbPerson.setItems(FXCollections.observableArrayList(PersonManagerHibernateImpl.getInstance().getAll()));

        scbTask.setItems(FXCollections.observableArrayList(TaskManagerHibernateImpl.getInstance().getAll()));

        scbTag.setItems(FXCollections.observableArrayList(TagManagerHibernateImpl.getInstance().getAll()));

        listPersons.setCellFactory(cell -> new PersonListCell());
        scbPerson.setCellFactory(cell -> new PersonListCell());

        scbTask.setCellFactory(cell -> new TaskListCell());
        listTasks.setCellFactory(cell -> new TaskListCell());
        scbTag.setCellFactory(cell -> new TagListCell());
        listTags.setCellFactory(cell -> new TagListCell());
    }

    @FXML
    public void handleAddTask(ActionEvent actionEvent) {
        listTasks.getItems().add(scbTask.getValue());
    }
    @FXML
    public void handleNewTask(ActionEvent actionEvent) {
        Task task = TaskManagerHibernateImpl.getInstance().showTaskView(this.getMainStage());
        listTasks.getItems().add(task);
        listTasks.refresh();
    }
    @FXML
    public void handleAddPerson(ActionEvent actionEvent) {
        listPersons.getItems().add(scbPerson.getValue());
    }
    @FXML
    public void handleNewPerson(ActionEvent actionEvent) {
        Person person = PersonManagerHibernateImpl.getInstance().showPersonView(this.getMainStage());
        if (person != null)
            listPersons.getItems().add(person);
        listPersons.refresh();
    }
    @FXML
    public void handleAddTag(ActionEvent actionEvent) {
        listTags.getItems().add(scbTag.getValue());
    }
    @FXML
    public void handleNewTag(ActionEvent actionEvent) {
        Tag tag = TagManagerHibernateImpl.getInstance().showTagView(this.getMainStage());
        if (tag != null)
            listTags.getItems().add(tag);
        listTags.refresh();
    }
    @FXML
    public void handleSave(ActionEvent actionEvent) {
        if (isInputValid()){
            document.setName(textFieldName.getText() == null ? "": textFieldName.getText());
            document.setRequisite(textFieldRequisite.getText() == null ? "": textFieldRequisite.getText());
            document.setPath(textFieldPath.getText() == null ? "": textFieldPath.getText());
            document.setDescription(textAreaDescription.getText() == null ? "": textAreaDescription.getText());
            document.setDate(datePickerDate.getValue());
            document.setPersons(new ArrayList<>(listPersons.getItems()));
            document.setTasks(new ArrayList<>(listTasks.getItems()));
            document.setTags(new ArrayList<>(listTags.getItems()));
            closeDialogView();
        }
    }
    @FXML
    public void handleCansel(ActionEvent actionEvent) {
        closeDialogView();
    }

    public void handleSelectPath(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();

//        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("*.*");
//        fileChooser.getExtensionFilters().add(extensionFilter);

        File file = fileChooser.showOpenDialog(this.getMainStage());

        if(file != null){
            textFieldPath.setText(file.getAbsolutePath());
        }
    }
    @Override
    public void setItem(SimpleItem item){
        this.document = (Document) item;
        textFieldName.setText(document.getName() == null ? "":document.getName());
        textFieldRequisite.setText(document.getRequisite() == null ? "":document.getRequisite());
        textAreaDescription.setText(document.getDescription() == null ? "":document.getDescription());
        datePickerDate.setValue(document.getDate());
        textFieldPath.setText(document.getPath());
        listPersons.getItems().addAll(document.getPersons());
        listTasks.getItems().addAll(document.getTasks());
        listTags.getItems().addAll(document.getTags());
    }

    private boolean isInputValid(){
        String errorMessage = " ";

        return true;
    }

}
