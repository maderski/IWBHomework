package maderski.iwbinterviewhw.Helpers;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewTreeObserver;

import maderski.iwbinterviewhw.Models.ViewRectModel;

/**
 * Created by Jason on 4/7/17.
 */

public class ViewRectHelper implements ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "ViewCoordHelper";

    private RecyclerView mRecyclerView;
    private SparseArray<ViewRectModel> mViewRectangles = new SparseArray<>();

    public ViewRectHelper(RecyclerView recyclerView){
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

            mViewRectangles.put(position, new ViewRectModel(rect.top, rect.right, rect.bottom, rect.left));
        }
        mRecyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

        for(int i=0; i<mViewRectangles.size(); i++){
            ViewRectModel viewRectangle = mViewRectangles.get(i);
            Log.d(TAG, "position: " + String.valueOf(i)
                    + "\ntop: " + String.valueOf(viewRectangle.getTop())
                    + " right: " + String.valueOf(viewRectangle.getRight())
                    + " bottom: " + String.valueOf(viewRectangle.getBottom())
                    + " left: " + String.valueOf(viewRectangle.getLeft()));
        }
    }

    public SparseArray<ViewRectModel> getViewRectangles(){
        return mViewRectangles;
    }
}
