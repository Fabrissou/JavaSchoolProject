package org.javaschool.data.old.dao;

import org.javaschool.data.old.conection.PoolConnectionBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BaseDao<T> {

    T get(long id);

    List<T> getAll();

    void save(T t);

    void delete(long id);

    default Connection getConnection() throws SQLException {
        return PoolConnectionBuilder.getConnection();
    }
}
