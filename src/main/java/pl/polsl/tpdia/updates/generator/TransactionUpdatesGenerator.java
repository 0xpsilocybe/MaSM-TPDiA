package pl.polsl.tpdia.updates.generator;

import pl.polsl.tpdia.helpers.EnumGenerator;
import pl.polsl.tpdia.helpers.TransactionGenerator;
import pl.polsl.tpdia.helpers.UpdateType;
import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;
import pl.polsl.tpdia.updates.handler.impl.account.TransactionMasmUpdateDescriptor;

import java.security.SecureRandom;
import java.util.List;

/**
 * Created by Szymon on 29.08.2016.
 */
public class TransactionUpdatesGenerator extends WorkerHelper {

    private final MasmUpdateWorker masmUpdateWorker;
    private final List<Integer> transactionIds;
    private final TransactionGenerator transactionGenerator;
    private final SecureRandom secureRandom;
    private final EnumGenerator<UpdateType> updateTypeGenerator;

    public TransactionUpdatesGenerator(MasmUpdateWorker masmUpdateWorker, List<Integer> transactionIds) {
        this.masmUpdateWorker = masmUpdateWorker;
        this.transactionIds = transactionIds;
        this.secureRandom = new SecureRandom();
        this.transactionGenerator = new TransactionGenerator(this.secureRandom);

        UpdateType t = UpdateType.INSERT;
        this.updateTypeGenerator = new EnumGenerator<>(t, this.secureRandom);
    }

    public void addTransactionId(int id) {
        transactionIds.add(id);
    }

    @Override
    public long getDelayBetweenOperations() {
        return 10;
    }

    @Override
    protected void doOperation() throws InterruptedException {

        UpdateType updateType = updateTypeGenerator.generate();
        MasmUpdateDescriptor<Transaction> descriptor = new TransactionMasmUpdateDescriptor(updateType);

        Transaction transaction = null;

        switch(updateType) {
            case INSERT: {
                transaction = transactionGenerator.generate();
                break;
            }
            case UPDATE: {
                int index = secureRandom.nextInt(transactionIds.size());
                transaction = transactionGenerator.generate();
                transaction.setId(transactionIds.get(index));
                break;
            }
            case DELETE: {
                int index = secureRandom.nextInt(transactionIds.size());
                transaction = new Transaction();
                transaction.setId(transactionIds.get(index));
                transactionIds.remove(index);
                break;
            }
        }

        descriptor.setUdpateDescriptor(transaction);
        masmUpdateWorker.queueUpdate(descriptor);
    }
}
