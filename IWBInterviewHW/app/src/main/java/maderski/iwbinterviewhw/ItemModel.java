package maderski.iwbinterviewhw;

/**
 * Created by Jason on 3/30/17.
 */

public class ItemModel {

    private String mText;
    private int mDrawable;

    public ItemModel(String text, int drawable){
        mText = text;
        mDrawable = drawable;
    }

    public String getText(){
        return mText;
    }

    public int getDrawable(){
        return mDrawable;
    }
}
