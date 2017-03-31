package maderski.iwbinterviewhw;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jason on 3/30/17.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private final ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    private List<ItemModel> mItemList;

    public RecyclerViewAdapter(List<ItemModel> itemList, ListItemClickListener listener){
        mOnClickListener = listener;
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
        holder.itemImage.setImageResource(mItemList.get(position).getDrawable());
        holder.itemText.setText(mItemList.get(position).getText());
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private static final String TAG = "RecyclerViewHolder";
        
        private ImageView itemImage;
        private TextView itemText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImage = (ImageView) itemView.findViewById(R.id.iv_item_image);
            itemText = (TextView) itemView.findViewById(R.id.tv_item_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int onClickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(onClickedPosition);
        }
    }
}
