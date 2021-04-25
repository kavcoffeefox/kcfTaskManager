package ru.kavcoffeefox.kcftaskmanager.utils;

import javafx.scene.control.ListCell;
import ru.kavcoffeefox.kcftaskmanager.entity.Tag;

public class TagListCell extends ListCell<Tag> {

    @Override
    protected void updateItem(Tag tag, boolean b) {
        super.updateItem(tag, b);
        if (b || tag == null) {
            setText("");
        } else {
            setText(tag.getName());
        }
    }
}