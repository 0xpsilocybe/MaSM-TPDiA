package pl.polsl.tpdia.updates;

import pl.polsl.tpdia.dao.Table;
import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.models.UpdateType;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Created by Szymon on 29.08.2016.
 */
public abstract class MasmUpdateDescriptor<TUpdateDescriptor, TDao extends Table<TUpdateDescriptor>>{

    protected TUpdateDescriptor updateDescriptor;
    protected final UpdateType updateType;
    protected final Timestamp timestamp;

    public MasmUpdateDescriptor(UpdateType updateType) {
        this.timestamp = new Timestamp(new java.util.Date().getTime());
        this.updateType = updateType;
    }

    public void setUpdateDescriptor(TUpdateDescriptor updateDescriptor) {
        this.updateDescriptor = updateDescriptor;
    }

    public abstract void processRecord(TDao transactionsDAO, Connection connection) throws SQLException;
}
