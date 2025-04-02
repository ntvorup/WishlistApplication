package org.example.wishlistapp.repository;

import java.util.List;

public abstract class Repository<T> implements IRepository<T> {
    @Override
    public abstract void addToDatabase(T newObjectToAdd);

    @Override
    public abstract void deleteFromDatabase(T objectToDelete) ;

    @Override
    public abstract T findById(int id);

    @Override
    public abstract List<T> getAll();

    @Override
    public abstract Boolean edit(T newObject);
}
