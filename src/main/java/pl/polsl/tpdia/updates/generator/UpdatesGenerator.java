package pl.polsl.tpdia.updates.generator;

import pl.polsl.tpdia.helpers.EnumGenerator;
import pl.polsl.tpdia.helpers.Generator;
import pl.polsl.tpdia.helpers.WorkerHelper;
import pl.polsl.tpdia.models.Model;
import pl.polsl.tpdia.models.UpdateType;
import pl.polsl.tpdia.updates.MasmUpdateDescriptor;
import pl.polsl.tpdia.updates.handler.MasmUpdateWorker;
import java.security.SecureRandom;
import java.util.List;

public abstract class UpdatesGenerator<TModel extends Model> extends WorkerHelper {

    private final MasmUpdateWorker<TModel> masmUpdateWorker;
    private final List<Integer> modelIds;
    protected Generator<TModel> modelGenerator;
    protected final SecureRandom secureRandom;
    private final EnumGenerator<UpdateType> updateTypeGenerator;

    public UpdatesGenerator(
            MasmUpdateWorker<TModel> masmUpdateWorker,
            List<Integer> modelIds) {
        super("Updates generator");
        this.masmUpdateWorker = masmUpdateWorker;
        this.modelIds = modelIds;
        this.secureRandom = new SecureRandom();
        this.updateTypeGenerator = new EnumGenerator<>(UpdateType.INSERT, this.secureRandom);
    }

    public void addModelId(int id) {
        modelIds.add(id);
    }

    @Override
    public long getDelayBetweenOperations() {
        return 10;
    }

    @Override
    protected void doOperation() throws InterruptedException {

        UpdateType updateType = updateTypeGenerator.generate();
        MasmUpdateDescriptor<TModel> descriptor = new MasmUpdateDescriptor<>(updateType);

        TModel model;

        switch(updateType) {
            case INSERT: {
                model = modelGenerator.generate();
                break;
            }
            case UPDATE: {
                int index = secureRandom.nextInt(modelIds.size());
                model = modelGenerator.generate();
                model.setId(modelIds.get(index));
                break;
            }
            case DELETE: {
                int index = secureRandom.nextInt(modelIds.size());
                model = modelGenerator.getNew();
                model.setId(modelIds.get(index));
                modelIds.remove(index);
                break;
            }
            default: {
                throw new EnumConstantNotPresentException(UpdateType.class, updateType.toString());
            }
        }

        descriptor.setModel(model);
        masmUpdateWorker.queueUpdate(descriptor);
    }
}
