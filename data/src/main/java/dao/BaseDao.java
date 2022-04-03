package dao;

import conection.PoolConnectionBuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface BaseDao<T> {

    T get(long id);

    List<T> getAll();

    void save(T t);

    void delete(T t);

    default Connection getConnection() throws SQLException {
        return PoolConnectionBuilder.getConnection();
    }
}
