package pl.polsl.tpdia.queries;

import pl.polsl.tpdia.models.Model;
import java.util.List;

public class MasmQueryResponseDescriptor<TModel extends Model> {

    private final List<TModel> queryResults;

    public MasmQueryResponseDescriptor(List<TModel> queryResults) {
        this.queryResults = queryResults;
    }

    public List<TModel> getQueryResults() {
        return queryResults;
    }
}
