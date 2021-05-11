package ru.kavcoffeefox.kcftaskmanager.utils;

import ru.kavcoffeefox.kcftaskmanager.entity.Document;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;
import ru.kavcoffeefox.kcftaskmanager.entity.Tag;
import ru.kavcoffeefox.kcftaskmanager.entity.Task;

import java.util.Set;

public class ItemUtil {

    private ItemUtil(){

    }

    public static String tagInOneLine(SimpleItem item){
        Set<Tag> tags;
        if (item instanceof Document){
            tags = ((Document) item).getTags();
        }
        else if (item instanceof Task){
            tags = ((Task) item).getTags();
        } else {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        tags.forEach(tag ->
                stringBuilder.append("#").append(tag.getName()).append("  "));
        return stringBuilder.toString();
    }

}
