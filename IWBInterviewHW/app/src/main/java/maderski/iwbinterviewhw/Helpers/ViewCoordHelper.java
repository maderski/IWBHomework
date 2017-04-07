package maderski.iwbinterviewhw.Helpers;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by Jason on 4/7/17.
 */

public class ViewCoordHelper implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "ViewCoordHelper";

    private RecyclerView mRecyclerView;

    public ViewCoordHelper(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
    }

    @Override
    public void onGlobalLayout() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

        for(int i = 0; i < layoutManager.getChildCount(); i++) {
            View view = layoutManager.getChildAt(i);
            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);
            Log.d(TAG, "top: " + String.valueOf(rect.top)
                    + " bottom: " + String.valueOf(rect.bottom)
                    + " left: " + String.valueOf(rect.left)
                    + " right: " + String.valueOf(rect.right));
        }
        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }
}
