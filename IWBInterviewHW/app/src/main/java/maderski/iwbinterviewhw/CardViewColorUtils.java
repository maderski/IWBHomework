package maderski.iwbinterviewhw;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Jason on 4/3/17.
 */

public class CardViewColorUtils {

    public static void setCardColor(Context context, RecyclerView recyclerView, int cardPosition, int color){
        View view = recyclerView.getChildAt(cardPosition);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_list_item_layout);
        linearLayout.setBackgroundColor(ContextCompat.getColor(context, color));
    }
}
