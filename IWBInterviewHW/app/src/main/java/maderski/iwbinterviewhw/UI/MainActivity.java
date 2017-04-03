package maderski.iwbinterviewhw.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.util.List;

import maderski.iwbinterviewhw.Models.ItemModel;
import maderski.iwbinterviewhw.R;
import maderski.iwbinterviewhw.Helpers.TextToSpeechHelper;
import maderski.iwbinterviewhw.Helpers.TouchEventsHelper;
import maderski.iwbinterviewhw.Utils.CardViewColorUtils;
import maderski.iwbinterviewhw.Utils.ItemListUtils;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ListItemTouchListener,
        TextToSpeechHelper.TextToSpeechCallback {
    private static final String TAG = "MainActivity";

    private List<ItemModel> mItemList;
    private TextToSpeechHelper mTextToSpeechHelper;
    private TouchEventsHelper mTouchEventsHelper;
    private RecyclerView mRecyclerView;

    private int mPosition;
    private boolean canPerformActions = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mItemList = ItemListUtils.getItemList();

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

    @Override
    public void onListItemPressed(int clickedItemIndex, float pressure, double areaOfEllipse) {
        mTouchEventsHelper.addTouchEvent(clickedItemIndex, pressure, areaOfEllipse);
        canPerformActions = true;

        CardViewColorUtils.setCardColor(this, mRecyclerView, clickedItemIndex, R.color.lightOrange);
    }

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
            findPosition();
            String itemText = mItemList.get(mPosition).getString(this);
            mTextToSpeechHelper.speakText(itemText);
        }
    }

    public int findPosition(){
        int position = mPosition;
        List<Integer> largestAreaPositions = mTouchEventsHelper.getOnClickPositions(mTouchEventsHelper.getLargestArea());
        if(largestAreaPositions.size() == 1){
            position = largestAreaPositions.get(0);
        } else if(!largestAreaPositions.isEmpty()){
            for(int p : largestAreaPositions){
                Log.d(TAG, "LARGEST AREA POSITIONS: " + String.valueOf(p));
            }
            position = mTouchEventsHelper.getOnClickPosition(mTouchEventsHelper.getLargestPressure());
        }
        Log.d(TAG, "POSITION CHOOSEN: " + String.valueOf(position));
        mPosition = position;
        return position;
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
