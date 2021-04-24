package ru.kavcoffeefox.kcftaskmanager.service.impl;

import javafx.stage.Stage;
import ru.kavcoffeefox.kcftaskmanager.dao.DocumentDAO;
import ru.kavcoffeefox.kcftaskmanager.dao.DocumentDAOHibernateImpl;
import ru.kavcoffeefox.kcftaskmanager.entity.Document;
import ru.kavcoffeefox.kcftaskmanager.entity.SimpleItem;
import ru.kavcoffeefox.kcftaskmanager.service.Manager;

import java.util.List;

public class DocumentManagerHibernateImpl extends AbstractManager implements Manager<Document, Integer> {
    private volatile static DocumentManagerHibernateImpl INSTANCE = null;
    private final DocumentDAO documentDAO;

    private DocumentManagerHibernateImpl() {
        documentDAO = new DocumentDAOHibernateImpl();
    }

    @Override
    public boolean add(Document document) {
        return documentDAO.add(document);
    }

    @Override
    public Document get(Integer id) {
        return documentDAO.get(id);
    }

    @Override
    public boolean delete(Integer id) {
        return documentDAO.delete(id);
    }

    @Override
    public boolean update(Integer id, Document item) {
        return documentDAO.update(id, item);
    }

    @Override
    public List<Document> getAll() {
        return documentDAO.getAll();
    }

    public static DocumentManagerHibernateImpl getInstance() {
        if (INSTANCE == null)
            synchronized (DocumentManagerHibernateImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DocumentManagerHibernateImpl();
                }
            }
        return INSTANCE;
    }

    public Document showDocumentView(Stage stage){
        Document document = new Document();
        showView(stage, document);
        documentDAO.add(document);
        return document;
    }

    public Document showDocumentView(Stage stage, Document document){
        showView(stage, document);
        documentDAO.update(document.getId(), document);
        return document;
    }

    @Override
    protected void showView(Stage stage, SimpleItem item) {
        showModalView(stage, "/view/modalwindows/DocumentView.fxml", item);
    }
}
