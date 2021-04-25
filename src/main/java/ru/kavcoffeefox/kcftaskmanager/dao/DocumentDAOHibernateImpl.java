package ru.kavcoffeefox.kcftaskmanager.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import ru.kavcoffeefox.kcftaskmanager.database.HibernateConnection;
import ru.kavcoffeefox.kcftaskmanager.entity.Document;

import java.util.List;

@Slf4j
public class DocumentDAOHibernateImpl implements DocumentDAO {
    @Override
    public boolean add(Document document) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.merge(document);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Document get(Integer id) {
        Document document;
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            document = session.get(Document.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return document;
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            Document document = session.get(Document.class, id);
            session.delete(document);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Integer id, Document document) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            Document localDocument = session.get(Document.class, id);
            localDocument.setName(document.getName());
            localDocument.setDate(document.getDate());
            localDocument.setDescription(document.getDescription());
            localDocument.setPath(document.getPath());
            localDocument.setRequisite(document.getRequisite());
            session.saveOrUpdate(localDocument);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Document> getAll() {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {

            session.beginTransaction();
            List<Document> documents = session.createQuery("from Document ").getResultList();
            documents.forEach( document -> {
                log.info(document.getPersons().toString());
                log.info(document.getTags().toString());
                log.info(document.getTasks().toString());
            });
            session.getTransaction().commit();
            return documents;
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Document document = new Document();
        DocumentDAOHibernateImpl d = new DocumentDAOHibernateImpl();
        d.add(document);
    }
}
