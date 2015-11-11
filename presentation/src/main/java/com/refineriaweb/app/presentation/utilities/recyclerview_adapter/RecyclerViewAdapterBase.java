package com.refineriaweb.app.presentation.utilities.recyclerview_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RecyclerViewAdapterBase<T, V extends View & ViewWrapper.Binder<T>> extends RecyclerView.Adapter<ViewWrapper<T, V>> {
    protected List<T> items = new ArrayList<T>();
    protected Listener<T, V> lister;

    @Override public final ViewWrapper<T, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    @Override public final void onBindViewHolder(ViewWrapper<T, V> viewHolder, int position) {
        T data = items.get(position);

        V view = viewHolder.getView();
        view.bind(data);
        if (lister != null) view.setOnClickListener(v -> lister.onClickItem(data, view));
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public void addListener(Listener<T, V> lister) {
        this.lister = lister;
    }

    public void add(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> collection) {
        items.addAll(collection);
        notifyDataSetChanged();
    }

    public void setAll(Collection<T> collection) {
        clear();
        addAll(collection);
    }

    public void clear() {
        items.clear();
    }

    public interface Listener<T, V> {
        void onClickItem(T t, V v);
    }
}
