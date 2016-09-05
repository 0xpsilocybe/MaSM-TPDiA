package pl.polsl.tpdia.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Contract for preparing secure sql statements
 */
interface PreparedStatementSetter {
    void setValues(PreparedStatement ps) throws SQLException;
}
