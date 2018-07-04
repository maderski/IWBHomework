package maderski.iwbinterviewhw.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

import maderski.iwbinterviewhw.helpers.ViewRectHelper;
import maderski.iwbinterviewhw.models.ItemModel;
import maderski.iwbinterviewhw.R;
import maderski.iwbinterviewhw.helpers.TextToSpeechHelper;
import maderski.iwbinterviewhw.helpers.CaptureTouchEventsHelper;
import maderski.iwbinterviewhw.managers.TouchEventsManager;
import maderski.iwbinterviewhw.utils.CardViewColorUtils;
import maderski.iwbinterviewhw.utils.ItemListUtils;

public class MainActivity extends AppCompatActivity implements TextToSpeechHelper.TextToSpeechCallback,
        TouchEventsManager.PositionCallbacks{
    private static final String TAG = "MainActivity";

    private List<ItemModel> mItemList;
    private TextToSpeechHelper mTextToSpeechHelper;
    private RecyclerView mRecyclerView;
    private ViewRectHelper mViewRectHelper;
    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        mItemList = ItemListUtils.getItemList();

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mRecyclerView.requestDisallowInterceptTouchEvent(true);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setAlignItems(AlignItems.CENTER);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        mRecyclerView.setLayoutManager(layoutManager);

        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mItemList);

        mRecyclerView.setAdapter(recyclerViewAdapter);

        final ViewTreeObserver viewTreeObserver = mRecyclerView.getViewTreeObserver();
        mViewRectHelper = new ViewRectHelper(mRecyclerView);
        viewTreeObserver.addOnGlobalLayoutListener(mViewRectHelper);

        View view = findViewById(R.id.v_touch_overlay);
        CaptureTouchEventsHelper captureTouchEventsHelper = new CaptureTouchEventsHelper(view);
        captureTouchEventsHelper.setOnTouchListener(new TouchEventsManager(mViewRectHelper, this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mTextToSpeechHelper == null) {
            mTextToSpeechHelper = new TextToSpeechHelper(this, 0.9f, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTextToSpeechHelper != null){
            mTextToSpeechHelper.shutdownTextToSpeech();
        }
    }

    // When done speaking, set colored cards back to white and then remove all touch events
    @Override
    public void doneSpeaking(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int i=0; i<mItemList.size(); i++) {
                    CardViewColorUtils.setCardColor(MainActivity.this, mRecyclerView, i, R.color.white);
                }
            }
        });
    }

    // When start speaking, change the card with the text being spoken to a darker orange color
    @Override
    public void startSpeaking(String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                CardViewColorUtils.setCardColor(MainActivity.this, mRecyclerView, mPosition, R.color.orange);
            }
        });
    }

    // Called after the largest area touched in a rectangle has been determined
    @Override
    public void choosenPosition(int position) {
        mPosition = position;
        Log.d(TAG, "Choosen Position: " + String.valueOf(position));
        String itemText = mItemList.get(mPosition).getString(this);
        mTextToSpeechHelper.speakText(itemText);
    }

    // Called when a touch occurs
    @Override
    public void pressedPositions(final List<Integer> positions) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Integer position : positions) {
                    Log.d(TAG, "TOUCHING POSITION: " + String.valueOf(position));
                    CardViewColorUtils.setCardColor(MainActivity.this, mRecyclerView, position, R.color.lightOrange);
                }
            }
        });
    }
}
