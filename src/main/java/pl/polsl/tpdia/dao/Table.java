package main.java.pl.polsl.tpdia.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Psilo on 29.08.2016.
 */
public interface Table<T> {
    public boolean create(Statement statement) throws SQLException;
    public Iterable<T> select(Connection connection, String where);
    public boolean insert(Connection connection, T item);
    public boolean insert(Statement statement, T item);
    public boolean update(Connection connection, T item);
    public boolean update(Statement statement, T item);
    public boolean delete(Connection connection, int id);
    public boolean delete(Statement statement, int id);
}
