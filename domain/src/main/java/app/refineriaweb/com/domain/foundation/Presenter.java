package app.refineriaweb.com.domain.foundation;

import app.refineriaweb.com.domain.sections.Wireframe;

public abstract class Presenter<V extends BaseView> implements Disposable {
    protected V view;
    protected final Wireframe wireframe;

    public Presenter(Wireframe wireframe) {
        this.wireframe = wireframe;
    }

    public void attachView(V view) {
        this.view = view;
    }
}