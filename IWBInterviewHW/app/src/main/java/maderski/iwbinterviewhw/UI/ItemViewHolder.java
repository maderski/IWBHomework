package maderski.iwbinterviewhw.UI;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import maderski.iwbinterviewhw.R;

/**
 * Created by Jason on 4/3/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {
    private static final String TAG = "RecyclerViewHolder";

    private ListItemTouchListener mOnTouchListener;

    public interface ListItemTouchListener {
        void onListItemPressed(int clickedItemIndex, float pressure, double areaOfEllipse);
        void onListItemReleased(int clickedItemIndex);
        void onListItemCancel();
    }

    private ImageView mItemImage;
    private TextView mItemText;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mItemImage = (ImageView) itemView.findViewById(R.id.iv_item_image);
        setImageSize(itemView);
        mItemText = (TextView) itemView.findViewById(R.id.tv_item_text);
        itemView.setOnTouchListener(this);
    }

    private void setImageSize(View itemView){
        float density  = itemView.getResources().getDisplayMetrics().density;
        float dpWidth  = itemView.getResources().getDisplayMetrics().widthPixels/density;
        float dpHeight = itemView.getResources().getDisplayMetrics().heightPixels/density;

        Log.d(TAG, "Density: " + String.valueOf(density)
                + "\nDPWidth: " + String.valueOf(dpWidth)
                + "\nDPHeight: " + String.valueOf(dpHeight));
        int imageWidth = (int)(dpWidth/1.75);
        int imageHeight = (int)(dpHeight/1.25);

        int height = (int)(imageHeight * density * .47f);
        int width = (int)(imageWidth * density * .5f);
        mItemImage.getLayoutParams().height = height;
        mItemImage.getLayoutParams().width = width;
        Log.d(TAG, "Width: " + String.valueOf(width)
                + "\nHeight: " + String.valueOf(height));
    }

    public void setOnTouchListener(ListItemTouchListener onTouchListener){
        mOnTouchListener = onTouchListener;
    }

    public void bindTo(int imageResource, int stringResource){
        mItemImage.setImageResource(imageResource);
        mItemText.setText(stringResource);
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
