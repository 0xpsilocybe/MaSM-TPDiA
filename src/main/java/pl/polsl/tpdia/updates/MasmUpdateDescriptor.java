package pl.polsl.tpdia.updates;

import pl.polsl.tpdia.models.UpdateType;

import java.sql.Timestamp;

public abstract class MasmUpdateDescriptor<TModel> {

    protected TModel model;

    protected final UpdateType updateType;

    protected final Timestamp timestamp;

    public MasmUpdateDescriptor(UpdateType updateType) {
        this.timestamp = new Timestamp(new java.util.Date().getTime());
        this.updateType = updateType;
    }

    public TModel getModel() {
        return model;
    }

    public void setModel(TModel model) {
        this.model = model;
    }

    public UpdateType getUpdateType() {
        return updateType;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return String.format("%1$10s: %2$6s, %3$TT",
                this.getModel().getClass().getSimpleName(),
                this.getUpdateType().toString(),
                this.getTimestamp());
    }
}
