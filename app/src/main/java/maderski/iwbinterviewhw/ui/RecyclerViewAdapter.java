package maderski.iwbinterviewhw.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import maderski.iwbinterviewhw.models.ItemModel;
import maderski.iwbinterviewhw.R;

/**
 * Created by Jason on 3/30/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private List<ItemModel> mItemList;

    public RecyclerViewAdapter(List<ItemModel> itemList){
        mItemList = itemList;
    }

    // Inflate view from layout, set OnTouchListener and create View Holder
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.card_view_list_item, parent, shouldAttachToParentImmediately);

        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }

    // Set the image and text for the View
    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindTo(mItemList.get(position).getDrawableResource(), mItemList.get(position).getStringResource());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
