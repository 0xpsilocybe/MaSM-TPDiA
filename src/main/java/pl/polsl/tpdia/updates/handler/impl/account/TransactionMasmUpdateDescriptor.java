package pl.polsl.tpdia.updates.handler.impl.account;

import pl.polsl.tpdia.dao.TransactionsDAO;
import pl.polsl.tpdia.models.UpdateType;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Szymon on 06.09.2016.
 */
public class TransactionMasmUpdateDescriptor extends MasmUpdateDescriptor<Transaction, TransactionsDAO> {

    public TransactionMasmUpdateDescriptor(UpdateType updateType) {
        super(updateType);
    }

    @Override
    public void processRecord(TransactionsDAO transactionsDAO, Connection connection) throws SQLException {

        switch (updateType) {
            case INSERT:
                transactionsDAO.insert(connection, this.updateDescriptor);
                break;
            case UPDATE:
                transactionsDAO.update(connection, this.updateDescriptor);
                break;
            case DELETE:
                transactionsDAO.delete(connection, this.updateDescriptor.getId());
                break;
        }
    }
}
