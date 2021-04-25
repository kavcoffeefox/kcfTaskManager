package ru.kavcoffeefox.kcftaskmanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;
import ru.kavcoffeefox.kcftaskmanager.entity.Tag;

import java.net.URL;
import java.util.ResourceBundle;

public class TagViewController extends AbstractController {
    private Tag tag;
    @FXML
    TextField name;
    @FXML
    TextArea description;

    @FXML
    public void handleSave(){
        tag.setName(name.getText());
        tag.setDescription(description.getText());
        closeDialogView();
    }

    @FXML
    public void handleCansel(){

        closeDialogView();
    }

    @Override
    public void setItem(SimpleItem item) {
        tag = (Tag) item;
        name.setText(tag.getName());
        description.setText(tag.getDescription());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
