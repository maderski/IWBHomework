package maderski.iwbinterviewhw.Helpers;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import maderski.iwbinterviewhw.Models.ViewRectModel;

/**
 * Created by Jason on 4/7/17.
 */

public class ViewCoordHelper implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "ViewCoordHelper";

    private RecyclerView mRecyclerView;
    private List<ViewRectModel> mViewRectangles = new ArrayList<>();

    public ViewCoordHelper(RecyclerView recyclerView){
        mRecyclerView = recyclerView;
    }

    @Override
    public void onGlobalLayout() {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();

        for(int i = 0; i < layoutManager.getChildCount(); i++) {
            View view = layoutManager.getChildAt(i);

            int position = layoutManager.getPosition(view);

            Rect rect = new Rect();
            view.getGlobalVisibleRect(rect);

            mViewRectangles.add(new ViewRectModel(position, rect.top, rect.right, rect.bottom, rect.left));
//            Log.d(TAG, "position: " + String.valueOf(position) + "\ntop: " + String.valueOf(rect.top)
//                    + " right: " + String.valueOf(rect.right)
//                    + " bottom: " + String.valueOf(rect.bottom)
//                    + " left: " + String.valueOf(rect.left));
        }
        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        for(ViewRectModel viewRectangle : mViewRectangles){
            Log.d(TAG, "position: " + String.valueOf(viewRectangle.getPosition())
                    + "\ntop: " + String.valueOf(viewRectangle.getTop())
                    + " right: " + String.valueOf(viewRectangle.getRight())
                    + " bottom: " + String.valueOf(viewRectangle.getBottom())
                    + " left: " + String.valueOf(viewRectangle.getLeft()));
        }
    }
}
