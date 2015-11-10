package app.refineriaweb.com.domain.foundation;

public abstract class Presenter<V extends View> implements Disposable {
    protected V view;

    public void attachView(V view) {
        this.view = view;
    }
}