package pl.polsl.tpdia.updates;

import pl.polsl.tpdia.helpers.UpdateType;

import java.sql.Timestamp;

/**
 * Created by Szymon on 29.08.2016.
 */
public abstract class MasmUpdateDescriptor<TUpdateDescriptor> {

    protected TUpdateDescriptor udpateDescriptor;
    protected final UpdateType updateType;
    protected final Timestamp timestamp;

    public MasmUpdateDescriptor(UpdateType updateType) {
        this.timestamp = new Timestamp(new java.util.Date().getTime());
        this.updateType = updateType;
    }

    public void setUdpateDescriptor(TUpdateDescriptor udpateDescriptor) {
        this.udpateDescriptor = udpateDescriptor;
    }

    public abstract void processRecord();
}
