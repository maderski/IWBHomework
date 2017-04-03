package maderski.iwbinterviewhw.Utils;

import java.util.ArrayList;
import java.util.List;

import maderski.iwbinterviewhw.Models.ItemModel;
import maderski.iwbinterviewhw.R;

/**
 * Created by Jason on 4/3/17.
 */

public class ItemListUtils {
    public static List<ItemModel> getItemList(){
        List<ItemModel> itemList = new ArrayList<>();
        itemList.add(new ItemModel(R.string.leave_me_alone, R.drawable.bother));
        itemList.add(new ItemModel(R.string.breath_out, R.drawable.blow));
        itemList.add(new ItemModel(R.string.get_away, R.drawable.boo));
        itemList.add(new ItemModel(R.string.read_braille, R.drawable.braille_read));
        itemList.add(new ItemModel(R.string.play_with_blocks, R.drawable.build_blocks));
        itemList.add(new ItemModel(R.string.breath_out, R.drawable.breathe));

        return itemList;
    }
}
