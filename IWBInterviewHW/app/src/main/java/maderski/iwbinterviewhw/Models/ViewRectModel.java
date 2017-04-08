package maderski.iwbinterviewhw.Models;

/**
 * Created by Jason on 4/7/17.
 */

public class ViewRectModel {
    private int mTop, mRight, mBottom, mLeft;

    public ViewRectModel(int top, int right, int bottom, int left){
        mTop = top;
        mRight = right;
        mBottom = bottom;
        mLeft = left;
    }

    public int getTop() {
        return mTop;
    }

    public int getRight() {
        return mRight;
    }

    public int getBottom() {
        return mBottom;
    }

    public int getLeft() {
        return mLeft;
    }
}
