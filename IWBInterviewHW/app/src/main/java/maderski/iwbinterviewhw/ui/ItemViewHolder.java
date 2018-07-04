package maderski.iwbinterviewhw.ui;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import maderski.iwbinterviewhw.R;

/**
 * Created by Jason on 4/3/17.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "RecyclerViewHolder";

    private ImageView mItemImage;
    private TextView mItemText;

    public ItemViewHolder(View itemView) {
        super(itemView);
        mItemImage = (ImageView) itemView.findViewById(R.id.iv_item_image);
        setImageSize(itemView);
        mItemText = (TextView) itemView.findViewById(R.id.tv_item_text);
    }

    // Scales image size based on display resolution
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

    // Sets the image for the ImageView and text for the TextView
    public void bindTo(int imageResource, int stringResource){
        mItemImage.setImageResource(imageResource);
        mItemText.setText(stringResource);
    }
}
