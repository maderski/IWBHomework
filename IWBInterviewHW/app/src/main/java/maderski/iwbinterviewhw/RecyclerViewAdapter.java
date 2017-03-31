package maderski.iwbinterviewhw;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jason on 3/30/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private final ListItemTouchListener mOnTouchListener;

    public interface ListItemTouchListener {
        void onListItemPressed(int clickedItemIndex);
        void onListItemReleased(int clickedItemIndex);
    }

    private List<ItemModel> mItemList;

    public RecyclerViewAdapter(List<ItemModel> itemList, ListItemTouchListener listener){
        mOnTouchListener = listener;
        mItemList = itemList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.card_view_list_item, parent, shouldAttachToParentImmediately);
        ItemViewHolder viewHolder = new ItemViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.itemImage.setImageResource(mItemList.get(position).getDrawableResource());
        holder.itemText.setText(mItemList.get(position).getString(holder.itemText.getContext()));
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
        private static final String TAG = "RecyclerViewHolder";
        
        private ImageView itemImage;
        private TextView itemText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.iv_item_image);
            itemText = (TextView) itemView.findViewById(R.id.tv_item_text);
            itemView.setOnTouchListener(this);
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int onClickedPosition = getAdapterPosition();
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                Log.i(TAG, "PRESSED!");
                mOnTouchListener.onListItemPressed(onClickedPosition);
            } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                Log.i(TAG, "RELEASED!");
                mOnTouchListener.onListItemReleased(onClickedPosition);
            }
            return true;
        }
    }
}
