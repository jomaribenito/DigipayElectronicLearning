package ph.digipay.digipayelectroniclearning.common.base;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.subjects.PublishSubject;

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> items;

    public BaseRecyclerAdapter(List<T> items){
        this.items = items;
    }


    private PublishSubject<T> publishSubject = PublishSubject.create();

    public PublishSubject<T> getPublishSubject() {
        return publishSubject;
    }

    public BaseRecyclerAdapter() {
        items = new ArrayList<>();
    }

    public List<T> getItems() {
        return items;
    }

    public T getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items!= null? items.size():0;
    }

    public void setItems(List<T> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }

    public void clear(){
        if (this.items!=null) {
            this.items.clear();
            notifyDataSetChanged();
        }
    }

}

