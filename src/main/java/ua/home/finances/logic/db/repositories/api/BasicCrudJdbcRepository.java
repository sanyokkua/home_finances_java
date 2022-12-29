package ua.home.finances.logic.db.repositories.api;

import java.util.Optional;

public interface BasicCrudJdbcRepository<T, U> {
    T create(T entity);

    T update(T entity);

    boolean delete(U entityId);

    Optional<T> findById(U entityId);
}
