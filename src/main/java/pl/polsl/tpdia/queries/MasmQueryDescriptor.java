package pl.polsl.tpdia.queries;

import pl.polsl.tpdia.models.Model;
import pl.polsl.tpdia.models.QueryType;

import java.sql.Timestamp;

public class MasmQueryDescriptor<TModel extends Model> {

    private final Timestamp timestamp;
    private final QueryType queryType;

    public MasmQueryDescriptor(QueryType queryType) {
        this.timestamp = new Timestamp(new java.util.Date().getTime());
        this.queryType = queryType;
    }

    public QueryType getQueryType() {
        return this.queryType;
    }

    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public String toString() {
        return String.format("%1$10s, %2$TT",
                this.getQueryType().toString(),
                this.getTimestamp());
    }
}
