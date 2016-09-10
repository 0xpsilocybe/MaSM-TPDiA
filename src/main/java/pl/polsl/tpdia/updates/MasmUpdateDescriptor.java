package pl.polsl.tpdia.updates;

import pl.polsl.tpdia.models.Model;
import pl.polsl.tpdia.models.UpdateType;

import java.sql.Timestamp;

public class MasmUpdateDescriptor<TModel extends Model>{

    private TModel model;

    private final UpdateType updateType;

    private final Timestamp timestamp;

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
