package pl.polsl.tpdia.updates;

import pl.polsl.tpdia.models.UpdateType;

import java.sql.Timestamp;

public abstract class MasmUpdateDescriptor<TModel>{

    protected TModel model;

    protected final UpdateType updateType;

    protected final Timestamp timestamp;

    public MasmUpdateDescriptor(UpdateType updateType) {
        this.timestamp = new Timestamp(new java.util.Date().getTime());
        this.updateType = updateType;
    }
    public UpdateType getUpdateType() {
        return updateType;
    }

    public TModel getModel() {
        return model;
    }

    public void setModel(TModel model) {
        this.model = model;
    }
}
