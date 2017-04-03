package maderski.iwbinterviewhw;

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

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemTouchListener,
        TextToSpeechHelper.TextToSpeechCallback{
    private static final String TAG = "MainActivity";

    private Toast mToast;
    private List<ItemModel> mItemList;
    private TextToSpeechHelper mTextToSpeechHelper;
    private TouchEventsHelper mTouchEventsHelper;
    private RecyclerView mRecyclerView;
    private TextToSpeechHelper.TextToSpeechCallback mTextToSpeechCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mTextToSpeechCallback = this;

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
        if(mTextToSpeechHelper == null) {
            mTextToSpeechHelper = new TextToSpeechHelper(this, 0.8f, this);
        }

        if(mTouchEventsHelper == null) {
            mTouchEventsHelper = new TouchEventsHelper();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTextToSpeechHelper != null){
            mTextToSpeechHelper.shutdownTextToSpeech();
        }
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
        canPerformActions = true;

        CardViewColorUtils.setCardColor(this, mRecyclerView, clickedItemIndex, R.color.lightOrange);
    }
    int mPosition;
    boolean canPerformActions = true;
    @Override
    public void onListItemReleased(int clickedItemIndex) {
        Log.d(TAG, "Item RELEASED");
        speakOut(canPerformActions);
    }

    @Override
    public void onListItemCancel() {
        canPerformActions = true;
        speakOut(canPerformActions);
        Log.d(TAG, "CANCELLED");
    }

    public void speakOut(boolean canSpeak){
        if(canSpeak){
            canPerformActions = false;
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
                position = mPosition;
            }

            Log.d(TAG, "POSITION CHOOSEN: " + String.valueOf(position));
            mPosition = position;

            String itemText = mItemList.get(position).getString(this);
            mTextToSpeechHelper.speakText(itemText);
        }
    }

    @Override
    public void doneSpeaking(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<Integer> allPositionsList = mTouchEventsHelper.getAllOnClickPositions();
                if(allPositionsList.isEmpty()){
                    CardViewColorUtils.setCardColor(MainActivity.this, mRecyclerView, mPosition, R.color.white);
                }else {
                    for (int clickedPosition : allPositionsList) {
                        Log.d(TAG, "Turn White: " + String.valueOf(clickedPosition));
                        CardViewColorUtils.setCardColor(MainActivity.this, mRecyclerView, clickedPosition, R.color.white);
                    }
                    mTouchEventsHelper.removeAllTouchEvents();
                }
            }
        });
    }

    @Override
    public void startSpeaking(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CardViewColorUtils.setCardColor(MainActivity.this, mRecyclerView, mPosition, R.color.orange);
            }
        });
    }
}
