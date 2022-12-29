package ua.home.finances.logic.services.api;

public interface CrudService<T, U> {
    T create(T entity);

    T update(T entity);

    Result delete(U entityId);

    T findById(U entityId);
}
