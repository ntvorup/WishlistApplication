package org.example.wishlistapp.repository;

import java.util.List;

public interface IRepository<T> {
    void addToDatabase(T newObjectToAdd);
    void deleteFromDatabase(T objectToDelete);
    T findById (int id);
    List<T> getAll();
    Boolean edit(T newObject);
}
