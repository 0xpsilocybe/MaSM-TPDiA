package pl.polsl.tpdia.queries;

import pl.polsl.tpdia.models.QueryType;

import java.sql.Timestamp;

/**
 * Created by Szymon on 30.08.2016.
 */
public abstract class MasmQueryDescriptor<TQueryDescriptor> {

    protected final Timestamp timestamp;
    protected final QueryType queryType;

    public MasmQueryDescriptor(QueryType queryType) {
        this.timestamp = new Timestamp(new java.util.Date().getTime());
        this.queryType = queryType;
    }
}
