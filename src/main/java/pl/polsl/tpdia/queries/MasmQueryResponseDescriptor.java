package pl.polsl.tpdia.queries;

import java.util.List;

public abstract class MasmQueryResponseDescriptor<TModel> {

    private final List<TModel> queryResults;

    public MasmQueryResponseDescriptor(List<TModel> queryResults) {
        this.queryResults = queryResults;
    }

    public List<TModel> getQueryResults() {
        return queryResults;
    }
}
