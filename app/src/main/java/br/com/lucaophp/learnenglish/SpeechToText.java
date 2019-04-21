package br.com.lucaophp.learnenglish;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import br.com.lucaophp.learnenglish.MainActivity;


public class SpeechToText implements RecognitionListener {

    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private MainActivity mainActivity;
    private String text = "";
    private boolean isListening = false;
    private int MINIMUM_LENGTH_FOR_EXTRA_SPEECH_IN_MILLIS = 3000;
    final BackgroundVoiceListener backgroundVoiceListener;
    public TextView subtitle;

    public boolean isListening() {
        return isListening;
    }

    public void setListening(boolean listening) {
        isListening = listening;
    }

    public SpeechToText(){
        mainActivity = new MainActivity();
        subtitle = mainActivity.getSubtitle();
        backgroundVoiceListener = new BackgroundVoiceListener();
        speech = SpeechRecognizer.createSpeechRecognizer(MainActivity.getContext());
        speech.setRecognitionListener(this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.US);
        //recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, mainActivity.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, MINIMUM_LENGTH_FOR_EXTRA_SPEECH_IN_MILLIS);

    }

    @Override
    public void onReadyForSpeech(Bundle bundle) {
        setListening(false);
    }

    @Override
    public void onBeginningOfSpeech() {
        setListening(true);
    }

    @Override
    public void onRmsChanged(float v) {
        Log.i("Text", "onRmsChanged: " + v);

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        setListening(false);
    }

    @Override
    public void onError(int i) {
        MainActivity.setsubtitle(text);
        Log.i("Text","text: " + text);

    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList matches = bundle
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Log.i("OL",matches.size()+"");
        text = "";
        Log.i("Text","text: " + text);
        if(matches!=null)
            for (Object result : matches)
                text += result + "\n";
        Log.i("Text","text: " + text);
        MainActivity.setsubtitle(text);


    }

    @Override
    public void onPartialResults(Bundle partialResults) {

        setListening(false);

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }

    public class BackgroundVoiceListener extends Thread{
        public void run(){
            try {
                this.sleep(2000);
                Log.i("Text","islistening: " + isListening());
                if(!isListening()){
                    setListening(true);
                    recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
                    speech.startListening(recognizerIntent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
