package maderski.iwbinterviewhw;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Jason on 3/30/17.
 */

public class TextToSpeechHelper extends UtteranceProgressListener implements TextToSpeech.OnInitListener {
    private static final String TAG = "TextToSpeechHelper";

    private TextToSpeech mTextToSpeech;

    public TextToSpeechHelper(Context context, float speechRate){
        mTextToSpeech = new TextToSpeech(context, this);
        mTextToSpeech.setSpeechRate(speechRate);
        mTextToSpeech.setOnUtteranceProgressListener(this);
    }

    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS){
            int result = mTextToSpeech.setLanguage(Locale.US);

            if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(TAG, "This Language is not supported");
            }
        } else {
            Log.e(TAG, "Initilization Failed");
        }
    }

    public void speakText(CharSequence text){
        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params, "UniqueID");
    }

    @Override
    public void onStart(String s) {

    }

    @Override
    public void onDone(String s) {
        Log.d(TAG, "DONE");
    }

    @Override
    public void onError(String s) {

    }
}
