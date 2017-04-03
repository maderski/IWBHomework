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
        void onListItemPressed(int clickedItemIndex, float pressure, double areaOfEllipse);
        void onListItemReleased(int clickedItemIndex);
        void onListItemCancel();
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
            int actionIndex = motionEvent.getActionIndex();
            int pointerId = motionEvent.getPointerId(actionIndex);
            int action = motionEvent.getActionMasked();
            int onClickedPosition = getAdapterPosition();
            int pointerCount = motionEvent.getPointerCount();
            Double areaOfEllipse = Math.PI * motionEvent.getTouchMajor() * motionEvent.getTouchMinor();
            switch(action) {
                case MotionEvent.ACTION_DOWN:
                    Log.d(TAG, "pointer count: " + String.valueOf(pointerCount));
                    Log.d(TAG, "DOWN POINTER ID: " + String.valueOf(pointerId));
                    Log.d(TAG, "DOWN");
                    mOnTouchListener.onListItemPressed(onClickedPosition, motionEvent.getPressure(), areaOfEllipse);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.d(TAG, "UP POINTER ID: " + String.valueOf(pointerId));
                    Log.d(TAG, "UP");
                    mOnTouchListener.onListItemReleased(onClickedPosition);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mOnTouchListener.onListItemCancel();
                    break;
                }
            return true;
        }
    }
}
