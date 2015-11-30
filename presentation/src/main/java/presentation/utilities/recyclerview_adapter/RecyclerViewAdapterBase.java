package presentation.utilities.recyclerview_adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RecyclerViewAdapterBase<D, V extends View & ViewWrapper.Binder<D>> extends RecyclerView.Adapter<ViewWrapper<D, V>> {
    protected List<D> items = new ArrayList<>();
    protected Listener<D, V> lister;

    @Override public final ViewWrapper<D, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    @Override public final void onBindViewHolder(ViewWrapper<D, V> viewHolder, int position) {
        D data = items.get(position);

        V view = viewHolder.getView();
        view.bind(data);
        if (lister != null) view.setOnClickListener(v -> lister.onClickItem(data, view));
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public void addListener(Listener<D, V> lister) {
        this.lister = lister;
    }

    public void add(D item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(Collection<D> collection) {
        items.addAll(collection);
        notifyDataSetChanged();
    }

    public void setAll(Collection<D> collection) {
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
