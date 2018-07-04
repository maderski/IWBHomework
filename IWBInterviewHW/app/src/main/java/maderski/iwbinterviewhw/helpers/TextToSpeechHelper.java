package maderski.iwbinterviewhw.helpers;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

import java.util.Locale;

/**
 * Created by Jason on 3/30/17.
 */

public class TextToSpeechHelper extends UtteranceProgressListener implements TextToSpeech.OnInitListener {
    private static final String TAG = "TextToSpeechHelper";

   public interface TextToSpeechCallback {
       void doneSpeaking(String s);
       void startSpeaking(String s);
   }

    private TextToSpeech mTextToSpeech;
    private TextToSpeechCallback mCallback;

    public TextToSpeechHelper(Context context, float speechRate){
        mTextToSpeech = new TextToSpeech(context, this);
        mTextToSpeech.setSpeechRate(speechRate);
        mTextToSpeech.setOnUtteranceProgressListener(this);
    }

    public TextToSpeechHelper(Context context, float speechRate, TextToSpeechCallback callback){
        this(context, speechRate);
        mCallback = callback;
    }

    // Called on TTS initialization
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

    // Speak the specified text
    public void speakText(CharSequence text){
        Bundle params = new Bundle();
        params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "");
        mTextToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, params, "UniqueID");
    }

    // Releases TTS resources
    public void shutdownTextToSpeech(){
        mTextToSpeech.shutdown();
    }

    // Called when TTS starts speaking
    @Override
    public void onStart(String s) {
        Log.d(TAG, "START SPEAKING");
        if(mCallback != null){
            mCallback.startSpeaking(s);
        }
    }

    // Called when TTS is done speaking
    @Override
    public void onDone(String s) {
        Log.d(TAG, "DONE SPEAKING");
        if(mCallback != null){
            mCallback.doneSpeaking(s);
        }
    }

    // Called when TTS has an error
    @Override
    public void onError(String s) {
        Log.d(TAG, "SPEAKING ERROR");
    }
}
