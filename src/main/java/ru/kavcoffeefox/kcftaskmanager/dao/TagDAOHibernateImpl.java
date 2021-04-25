package ru.kavcoffeefox.kcftaskmanager.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import ru.kavcoffeefox.kcftaskmanager.database.HibernateConnection;
import ru.kavcoffeefox.kcftaskmanager.entity.Tag;

import java.util.List;

@Slf4j
public class TagDAOHibernateImpl implements TagDAO{
    @Override
    public boolean add(Tag tag) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.merge(tag);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public Tag get(Integer id) {
        Tag tag;
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            tag = session.get(Tag.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
        return tag;
    }

    @Override
    public boolean delete(Integer id) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            Tag tag = session.get(Tag.class, id);
            session.delete(tag);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Integer id, Tag tag) {
        try (Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()) {
            session.beginTransaction();
            Tag localTag = session.get(Tag.class, id);
            localTag.setName(tag.getName());
            localTag.setDescription(tag.getDescription());
            session.saveOrUpdate(localTag);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Tag> getAll() {
        try(Session session = HibernateConnection.getInstance().getFactory().getCurrentSession()){
            session.beginTransaction();
            List<Tag> tags = session.createQuery("from Tag ").getResultList();
            session.getTransaction().commit();
            return tags;
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
