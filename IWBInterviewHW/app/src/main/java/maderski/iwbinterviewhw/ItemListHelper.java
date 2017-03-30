package maderski.iwbinterviewhw;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 3/30/17.
 */

public class ItemListHelper {
    private static final String TAG = "ItemListHelper";

    private List<ItemModel> mItemsList = new ArrayList<>();

    public void addItemToList(String itemText, int itemDrawable){
        ItemModel item = new ItemModel(itemText, itemDrawable);
        mItemsList.add(item);
    }

    public List<ItemModel> getAllItemsOnList(){
        return mItemsList;
    }
}
