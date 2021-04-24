package ru.kavcoffeefox.kcftaskmanager.service.impl;

import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.dao.TagDAO;
import ru.kavcoffeefox.kcftaskmanager.dao.TagDAOHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;
import ru.kavcoffeefox.kcftaskmanager.entity.Tag;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;

import java.util.List;

public class TagManagerHibernateImpl extends AbstractManager implements Manager<Tag, Integer> {
    private volatile static TagManagerHibernateImpl INSTANCE = null;
    private static TagDAO tagDAO;
    private TagManagerHibernateImpl(){
        tagDAO = new TagDAOHibernateImpl();
    }

    @Override
    public boolean add(Tag item) {
        return false;
    }

    @Override
    public Tag get(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean update(Integer id, Tag item) {
        return false;
    }

    @Override
    public List<Tag> getAll() {
        return null;
    }

    @Override
    protected void showView(Stage stage, SimpleItem item) {
//        showModalView();
    }

    public static TagManagerHibernateImpl getInstance() {
        if (INSTANCE == null)
            synchronized (TagManagerHibernateImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TagManagerHibernateImpl();
                }
            }
        return INSTANCE;
    }

    public Tag showTagView(Stage stage){
        Tag tag = new Tag();
        showView(stage, tag);
        tagDAO.add(tag);
        return tag;
    }

    public Tag showPersonView(Stage stage, Tag tag){
        showView(stage, tag);
        tagDAO.update(tag.getId(), tag);
        return tag;
    }

}
