package app.refineriaweb.com.domain.presenters;

import app.refineriaweb.com.domain.views.View;

public abstract class Presenter<V extends View> {
    protected V view;

    public void attachView(V view) {
        this.view = view;
    }

    public abstract void destroy();
}