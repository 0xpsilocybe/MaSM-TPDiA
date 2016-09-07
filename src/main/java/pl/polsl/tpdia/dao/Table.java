package pl.polsl.tpdia.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Basic database table operations
 * @param <T> Database model
 */
public interface Table<T> {
    boolean create(Connection connection) throws SQLException;
    T selectById(Connection connection, int id) throws SQLException;
    List<T> selectAll(Connection connection) throws SQLException;
    int insert(Connection connection, T item) throws SQLException;
    boolean update(Connection connection, T item) throws SQLException;
    boolean delete(Connection connection, int id) throws SQLException;
}
