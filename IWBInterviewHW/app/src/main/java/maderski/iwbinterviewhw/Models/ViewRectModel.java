package maderski.iwbinterviewhw.Models;

/**
 * Created by Jason on 4/7/17.
 */

public class ViewRectModel {
    private int mPosition, mTop, mRight, mBottom, mLeft;

    public ViewRectModel(int position, int top, int right, int bottom, int left){
        mPosition = position;
        mTop = top;
        mRight = right;
        mBottom = bottom;
        mLeft = left;
    }

    public int getPosition() {
        return mPosition;
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
