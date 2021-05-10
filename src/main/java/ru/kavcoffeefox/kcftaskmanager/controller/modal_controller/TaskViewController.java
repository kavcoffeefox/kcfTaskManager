package ru.kavcoffeefox.kcftaskmanager.controller.modal_controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.SearchableComboBox;
import ru.kavcoffeefox.kcftaskmanager.controller.AbstractController;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;
import ru.kavcoffeefox.kcftaskmanager.entity.Tag;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;
import ru.kavcoffeefox.kcftaskmanager.service.impl.PersonManagerHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.service.impl.TagManagerHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.utils.PersonListCell;
import ru.kavcoffeefox.kcftaskmanager.utils.TagListCell;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;

public class TaskViewController extends AbstractController {
    private Task task;

    @FXML
    private TextField textFieldTaskName;
    @FXML
    private TextField textFieldTaskType;
    @FXML
    private TextField textEditPeriod;
    @FXML
    private DatePicker datePickerDeadline;
    @FXML
    public SearchableComboBox<Tag> scbTag;
    @FXML
    private TextArea textAreaDescription;
    @FXML
    private ListView<Person> listPeople;
    @FXML
    private ListView<Tag> listTags;
    @FXML
    private SearchableComboBox<Person> scbExecutors;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem item1 = new MenuItem("Удалить");
        item1.setOnAction(event -> {
            if (listPeople.getSelectionModel().getSelectedItem() != null)
                listPeople.getItems().remove(listPeople.getSelectionModel().getSelectedItem());
        });

        contextMenu.getItems().addAll(item1);
        this.listPeople.setContextMenu(contextMenu);

        scbExecutors.setItems(FXCollections.observableArrayList(PersonManagerHibernateImpl.getInstance().getAll()));
        scbExecutors.setMaxWidth(Double.MAX_VALUE);

        listPeople.setCellFactory(cell -> new PersonListCell());
        scbExecutors.setCellFactory(cell -> new PersonListCell());
        scbExecutors.setConverter(
                new StringConverter<>() {
                    @Override
                    public String toString(Person person) {
                        return person == null ? "" : Person.getFIO(person);
                    }

                    @Override
                    public Person fromString(String s) {
                        return null;
                    }
                });

        scbTag.setCellFactory(cell -> new TagListCell());
        listTags.setCellFactory(cell -> new TagListCell());

        scbTag.setConverter(
                new StringConverter<>() {
                    @Override
                    public String toString(Tag tag) {
                        return tag == null ? "" : tag.getName();
                    }

                    @Override
                    public Tag fromString(String s) {
                        return null;
                    }
                });

        scbTag.setItems(FXCollections.observableArrayList(TagManagerHibernateImpl.getInstance().getAll()));
    }

    @FXML
    private void handleAddExecutor(){
        listPeople.getItems().add(scbExecutors.getValue());
    }

    @FXML void handleNewPerson(){
        Person person = PersonManagerHibernateImpl.getInstance().showPersonView(this.getMainStage());
        if (person != null)
            listPeople.getItems().add(person);
    }


    @FXML
    private void handleOk(){
        if (isInputValid()){
            task.setName(textFieldTaskName.getText());
            task.setType(textFieldTaskType.getText());
            task.setPeriod(Integer.parseInt(textEditPeriod.getText()));
            task.setDescription(textAreaDescription.getText());
            task.setDeadline(datePickerDeadline.getValue());
            task.setExecutors(new HashSet<>(listPeople.getItems()));
            task.setTags(new HashSet<>(listTags.getItems()));

            setOkClicked(true);
            closeDialogView();
        }
    }

    @FXML
    private void handleCansel(){
        setOkClicked(false);
        closeDialogView();
    }

    @Override
    public void setItem(SimpleItem item){
        this.task = (Task) item;
        textFieldTaskName.setText(task.getName() == null ? "": task.getName());
        datePickerDeadline.setValue(task.getDeadline());
        listPeople.setItems(FXCollections.observableArrayList(task.getExecutors()));
        listTags.setItems(FXCollections.observableArrayList(task.getTags()));
        textFieldTaskType.setText(task.getType() == null ? "": task.getType());
        textEditPeriod.setText(String.valueOf(task.getPeriod()));
        textAreaDescription.setText(task.getDescription() == null ? "" : task.getDescription());
    }

    @FXML
    public void handleAddTag() {
        listTags.getItems().add(scbTag.getValue());
    }
    @FXML
    public void handleNewTag() {
        Tag tag = TagManagerHibernateImpl.getInstance().showTagView(this.getMainStage());
        if (tag != null)
            listTags.getItems().add(tag);
        listTags.refresh();
    }

    private boolean isInputValid(){

        return true;
    }

}
