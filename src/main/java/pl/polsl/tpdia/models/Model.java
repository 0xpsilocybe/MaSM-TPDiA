package pl.polsl.tpdia.models;

/**
 * Base model
 * Created by Szymon on 10.09.2016.
 */
public abstract class Model {
    private int id;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
