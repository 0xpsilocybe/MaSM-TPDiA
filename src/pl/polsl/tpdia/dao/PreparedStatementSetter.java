package pl.polsl.tpdia.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Psilo on 30.08.2016.
 */
public interface PreparedStatementSetter {
    void setValues(PreparedStatement ps) throws SQLException;
}
