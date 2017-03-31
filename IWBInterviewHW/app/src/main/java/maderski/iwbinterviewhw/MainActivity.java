package maderski.iwbinterviewhw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemClickListener{

    private Toast mToast;
    private List<ItemModel> mItemList;
    private TextToSpeechHelper mTextToSpeechHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mTextToSpeechHelper = new TextToSpeechHelper(this, 0.7f);
        mItemList = getItemList();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mItemList, this);

        recyclerView.setAdapter(recyclerViewAdapter);

    }

    private List<ItemModel> getItemList(){
        List<ItemModel> itemList = new ArrayList<>();
        itemList.add(new ItemModel(R.string.leave_me_alone, R.drawable.bother));
        itemList.add(new ItemModel(R.string.breath_out, R.drawable.blow));
        itemList.add(new ItemModel(R.string.get_away, R.drawable.boo));
        itemList.add(new ItemModel(R.string.read_braille, R.drawable.braille_read));
        itemList.add(new ItemModel(R.string.play_with_blocks, R.drawable.build_blocks));
        itemList.add(new ItemModel(R.string.breath_out, R.drawable.breathe));

        return itemList;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
//        if(mToast != null){
//            mToast.cancel();
//        }
//
//        mToast = Toast.makeText(this, "Item number clicked: " + String.valueOf(clickedItemIndex), Toast.LENGTH_LONG);
//        mToast.show();
        String itemText = mItemList.get(clickedItemIndex).getString(this);
        mTextToSpeechHelper.speakText(itemText);
    }
}
