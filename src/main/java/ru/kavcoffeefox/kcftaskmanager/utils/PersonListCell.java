package ru.kavcoffeefox.kcftaskmanager.utils;

import javafx.scene.control.ListCell;
import ru.kavcoffeefox.kcftaskmanager.entity.Person;

public class PersonListCell extends ListCell<Person> {

    @Override
    protected void updateItem(Person person, boolean b) {
        super.updateItem(person, b);
        if (b || person == null) {
            setText("");
        } else {
            setText(Person.getFIO(person));
        }
    }
}