package maderski.iwbinterviewhw.Utils;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import maderski.iwbinterviewhw.R;

/**
 * Created by Jason on 4/3/17.
 */

public class CardViewColorUtils {

    public static void setCardColor(Context context, RecyclerView recyclerView, int cardPosition, int color){
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(cardPosition);
        if(viewHolder != null) {
            LinearLayout linearLayout = (LinearLayout) viewHolder.itemView.findViewById(R.id.ll_list_item_layout);
            linearLayout.setBackgroundColor(ContextCompat.getColor(context, color));
        }
    }
}
