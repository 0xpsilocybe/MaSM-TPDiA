package pl.polsl.tpdia.updates.generator;

import pl.polsl.tpdia.helpers.EnumGenerator;
import pl.polsl.tpdia.helpers.TransactionGenerator;
import pl.polsl.tpdia.models.UpdateType;
import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;
import pl.polsl.tpdia.updates.handler.impl.transaction.TransactionMasmUpdateDescriptor;

import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class TransactionUpdatesGenerator extends WorkerHelper {

    private final MasmUpdateWorker<Transaction> masmUpdateWorker;
    private final List<Integer> transactionIds;
    private final TransactionGenerator transactionGenerator;
    private final SecureRandom secureRandom;
    private final EnumGenerator<UpdateType> updateTypeGenerator;

    public TransactionUpdatesGenerator(
            MasmUpdateWorker<Transaction> masmUpdateWorker,
            List<Integer> transactionIds) {
        super("Updates generator");
        this.masmUpdateWorker = masmUpdateWorker;
        this.transactionIds = transactionIds;
        this.secureRandom = new SecureRandom();
        this.transactionGenerator = new TransactionGenerator(this.secureRandom);
        this.updateTypeGenerator = new EnumGenerator<>(UpdateType.INSERT, this.secureRandom);
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
        UpdateType updateType = UpdateType.INSERT;
        if (transactionIds.size() > 0) {
            updateType = updateTypeGenerator.generate();
        }

        MasmUpdateDescriptor<Transaction> descriptor = new TransactionMasmUpdateDescriptor(updateType);
        Transaction transaction;

        switch(updateType) {
            case INSERT: {
                logger.trace("Generating transaction for INSERT.");
                transaction = transactionGenerator.generate();
                break;
            }
            case UPDATE: {
                logger.trace("Generating transaction for UPDATE.");
                int index = secureRandom.nextInt(transactionIds.size());
                transaction = transactionGenerator.generate();
                transaction.setId(transactionIds.get(index));
                break;
            }
            case DELETE: {
                logger.trace("Generating transaction for DELETE.");
                int index = secureRandom.nextInt(transactionIds.size());
                transaction = new Transaction();
                transaction.setId(transactionIds.get(index));
                transactionIds.remove(index);
                break;
            }
            default: {
                throw new EnumConstantNotPresentException(UpdateType.class, updateType.toString());
            }
        }

        descriptor.setModel(transaction);
        masmUpdateWorker.queueUpdate(descriptor);
    }
}
