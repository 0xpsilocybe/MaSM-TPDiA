package pl.polsl.tpdia.updates.generator;

import pl.polsl.tpdia.helpers.EnumGenerator;
import pl.polsl.tpdia.helpers.Generator;
import pl.polsl.tpdia.helpers.TransactionGenerator;
import pl.polsl.tpdia.models.Model;
import pl.polsl.tpdia.models.UpdateType;
import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.models.Transaction;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;
import pl.polsl.tpdia.updates.handler.impl.transaction.TransactionMasmUpdateDescriptor;

import java.security.SecureRandom;
import java.util.List;

public class UpdatesGenerator<TModel extends Model, TGenerator extends Generator<TModel>> extends WorkerHelper {

    private final MasmUpdateWorker<TModel> masmUpdateWorker;
    private final List<Integer> transactionIds;
    private final Generator<TModel> transactionGenerator;
    private final SecureRandom secureRandom;
    private final EnumGenerator<UpdateType> updateTypeGenerator;

    public UpdatesGenerator(MasmUpdateWorker<TModel> masmUpdateWorker, List<Integer> transactionIds) {
        this.masmUpdateWorker = masmUpdateWorker;
        this.transactionIds = transactionIds;
        this.secureRandom = new SecureRandom();
        this.transactionGenerator = new TGenerator(this.secureRandom);
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

        UpdateType updateType = updateTypeGenerator.generate();
        MasmUpdateDescriptor<TModel> descriptor = new MasmUpdateDescriptor<>(updateType);

        TModel transaction;

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
                transaction = new TModel();
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
