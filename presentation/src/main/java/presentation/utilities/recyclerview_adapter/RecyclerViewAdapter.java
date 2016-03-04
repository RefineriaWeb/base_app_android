package presentation.utilities.recyclerview_adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RecyclerViewAdapter<T, V extends View & ViewWrapper.Binder<T>> extends RecyclerView.Adapter<ViewWrapper<T, V>> {
    protected List<T> items = new ArrayList<>();
    protected Listener<T, V> listener;

    @Override public final ViewWrapper<T, V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(onCreateItemView(parent, viewType));
    }

    protected abstract V onCreateItemView(ViewGroup parent, int viewType);

    @Override public final void onBindViewHolder(ViewWrapper<T, V> viewHolder, int position) {
        T data = items.get(position);

        V view = viewHolder.getView();
        view.bind(data, position);
        if (listener != null) view.setOnClickListener(v -> listener.onClickItem(data, view));
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public void addListener(Listener<T, V> lister) {
        this.listener = lister;
    }

    public void add(T item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void addAll(List<T> data) {
        items.addAll(data);
        notifyDataSetChanged();
    }

    public void setAll(List<T> data) {
        clear();
        addAll(data);
    }

    public List<T> getAll() {
        return items;
    }

    public void clear() {
        items.clear();
    }

    public interface Listener<T, V> {
        void onClickItem(T t, V v);
    }

    public SwipeRemoveAction swipeToRemoveItemOn(final RecyclerView recyclerView) {
        return new SwipeRemoveAction(recyclerView);
    }

    public class SwipeRemoveAction {
        private final RecyclerView recyclerView;
        private OnItemRemoved onItemRemoved;
        private String titleAction = "Undo", descriptionAction = "Item removed";
        private boolean redrawOnRemovedItem, undoAction;

        public SwipeRemoveAction(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;

            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    final int position = viewHolder.getAdapterPosition();
                    T itemRemoved = items.get(position);
                    items.remove(position);
                    notifyItemRemoved(position);

                    if (undoAction) showSnackbarUndo(itemRemoved, position);
                    else if (onItemRemoved != null) onItemRemoved.onRemoved(itemRemoved);
                }
            });
            itemTouchHelper.attachToRecyclerView(recyclerView);
        }

        public SwipeRemoveAction notify(OnItemRemoved<T> onItemRemoved) {
            this.onItemRemoved = onItemRemoved;
            return this;
        }

        public SwipeRemoveAction withUndoAction() {
            this.undoAction = true;
            return this;
        }

        public SwipeRemoveAction withTitleAction(String titleAction) {
            this.titleAction = titleAction;
            return this;
        }

        public SwipeRemoveAction withDescriptionAction(String descriptionAction) {
            this.descriptionAction = descriptionAction;
            return this;
        }

        public SwipeRemoveAction redrawAfterRemoved(boolean redrawOnRemovedItem) {
            this.redrawOnRemovedItem = redrawOnRemovedItem;
            return this;
        }

        private void showSnackbarUndo(final T itemRemoved, final int position) {
            Snackbar.make(recyclerView, descriptionAction, Snackbar.LENGTH_LONG)
                    .setCallback(new Snackbar.Callback() {
                        @Override public void onDismissed(Snackbar snackbar, int event) {
                            if (redrawOnRemovedItem) notifyDataSetChanged();
                            if (onItemRemoved != null && event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) onItemRemoved.onRemoved(itemRemoved);
                        }
                    })
                    .setAction(titleAction, v -> {
                        v.setEnabled(false); //prevent multiple clicks
                        items.add(position, itemRemoved);
                        notifyItemInserted(position);
                    }).show();
        }
    }


    public interface OnItemRemoved<T> {
        void onRemoved(T item);
    }
}
