package maderski.iwbinterviewhw.models;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Jason on 3/30/17.
 */

public class ItemModel {

    private int mStringResource;
    private int mDrawableResource;

    public ItemModel(int stringResource, int drawableResource){
        mStringResource = stringResource;
        mDrawableResource = drawableResource;
    }

    public int getStringResource(){
        return mStringResource;
    }

    public String getString(Context context){
        return context.getString(mStringResource);
    }

    public int getDrawableResource(){
        return mDrawableResource;
    }

    public Drawable getDrawable(Context context){
        return context.getDrawable(mDrawableResource);
    }
}
