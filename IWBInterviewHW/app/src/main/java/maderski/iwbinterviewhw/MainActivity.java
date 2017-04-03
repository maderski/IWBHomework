package maderski.iwbinterviewhw;

import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemTouchListener {
    private static final String TAG = "MainActivity";

    private Toast mToast;
    private List<ItemModel> mItemList;
    private TextToSpeechHelper mTextToSpeechHelper;
    private TouchEventsHelper mTouchEventsHelper;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mItemList = getItemList();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mItemList, this);

        mRecyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mTextToSpeechHelper = new TextToSpeechHelper(this, 0.8f);
        mTouchEventsHelper = new TouchEventsHelper();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTextToSpeechHelper = null;
        mTouchEventsHelper = null;
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
    public void onListItemPressed(int clickedItemIndex, float pressure, double areaOfEllipse) {
        mTouchEventsHelper.addTouchEvent(clickedItemIndex, pressure, areaOfEllipse);
        doSomething = true;

        View view = mRecyclerView.getChildAt(clickedItemIndex);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_list_item_layout);
        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.lightOrange));

//        String itemText = mItemList.get(clickedItemIndex).getString(this);
//        mTextToSpeechHelper.speakText(itemText);
    }
    int lastPosition;
    boolean doSomething = true;
    @Override
    public void onListItemReleased(int clickedItemIndex) {
        Log.d(TAG, "Item RELEASED");
        speakOut(doSomething);

//        View view = mRecyclerView.getChildAt(clickedItemIndex);
//        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_list_item_layout);
//        linearLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
    }

    @Override
    public void onListItemCancel() {
        doSomething = true;
        speakOut(doSomething);
        mTouchEventsHelper.removeAllTouchEvents();
        Log.d(TAG, "CANCELLED");
    }

    public void speakOut(boolean canSpeak){
        if(canSpeak){
            doSomething = false;
            int position;
            List<Integer> largestAreaPositions = mTouchEventsHelper.getOnClickPositions(mTouchEventsHelper.getLargestArea());
            if(largestAreaPositions.size() == 1){
                position = largestAreaPositions.get(0);
            } else if(!largestAreaPositions.isEmpty()){
                for(int p : largestAreaPositions){
                    Log.d(TAG, "LARGEST AREA POSITIONS: " + String.valueOf(p));
                }
                position = mTouchEventsHelper.getOnClickPosition(mTouchEventsHelper.getLargestPressure());
            } else {
                position = lastPosition;
            }

            Log.d(TAG, "POSITION CHOOSEN: " + String.valueOf(position));
            lastPosition = position;

            View view = mRecyclerView.getChildAt(position);
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.ll_list_item_layout);
            linearLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));

            String itemText = mItemList.get(position).getString(this);
            mTextToSpeechHelper.speakText(itemText);

            for(int clickedPosition : mTouchEventsHelper.getAllOnClickPositions()) {
                view = mRecyclerView.getChildAt(clickedPosition);
                linearLayout = (LinearLayout) view.findViewById(R.id.ll_list_item_layout);
                linearLayout.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.white));
            }
            mTouchEventsHelper.removeAllTouchEvents();
        }
    }
}
