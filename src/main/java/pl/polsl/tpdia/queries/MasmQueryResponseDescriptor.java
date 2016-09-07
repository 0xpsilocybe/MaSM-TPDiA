package pl.polsl.tpdia.queries;

import java.util.List;

/**
 * Created by Szymon on 30.08.2016.
 */
public abstract class MasmQueryResponseDescriptor<TQueryResponseDescriptor> {

    private final List<TQueryResponseDescriptor> queryResults;

    public MasmQueryResponseDescriptor(List<TQueryResponseDescriptor> queryResults) {
        this.queryResults = queryResults;
    }

    public List<TQueryResponseDescriptor> getQueryResults() {
        return queryResults;
    }
}
