package ru.kavcoffeefox.kcftaskmanager.dao;

import java.util.List;

public interface DAO<T,I>{
    boolean add(T item);

    T get(I id);

    boolean delete(I id);

    boolean update(I id, T item);

    List<T> getAll();
}
