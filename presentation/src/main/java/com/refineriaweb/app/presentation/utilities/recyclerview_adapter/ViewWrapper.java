package com.refineriaweb.app.presentation.utilities.recyclerview_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ViewWrapper<T, V extends View & ViewWrapper.Binder<T>> extends RecyclerView.ViewHolder {
    private final V view;

    public ViewWrapper(V itemView) {
        super(itemView);
        view = itemView;
    }

    public V getView() {
        return view;
    }

    public interface Binder<T> {
        void bind(T data);
    }
}
