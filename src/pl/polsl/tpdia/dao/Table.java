package pl.polsl.tpdia.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Created by Psilo on 29.08.2016.
 */
public interface Table<T> {
    public boolean create(Connection connection) throws SQLException;
    public T selectById(Connection connection, int id);
    public Collection<T> selectAll(Connection connection);
    public boolean insert(Connection connection, T item);
    public boolean update(Connection connection, T item);
    public boolean delete(Connection connection, int id);
}
