package maderski.iwbinterviewhw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemClickListener{

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getItemList(), this);

        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private List<ItemModel> getItemList(){
        ItemListHelper itemListHelper = new ItemListHelper();
        itemListHelper.addItemToList(getString(R.string.leave_me_alone), R.drawable.bother);
        itemListHelper.addItemToList(getString(R.string.breath_out), R.drawable.blow);
        itemListHelper.addItemToList(getString(R.string.get_away), R.drawable.boo);
        itemListHelper.addItemToList(getString(R.string.read_braille), R.drawable.braille_read);
        itemListHelper.addItemToList(getString(R.string.play_with_blocks), R.drawable.build_blocks);
        itemListHelper.addItemToList(getString(R.string.breath_out), R.drawable.breathe);

        return itemListHelper.getAllItemsOnList();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        if(mToast != null){
            mToast.cancel();
        }

        mToast = Toast.makeText(this, "Item number clicked: " + String.valueOf(clickedItemIndex), Toast.LENGTH_LONG);
        mToast.show();

    }
}
