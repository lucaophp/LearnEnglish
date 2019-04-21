package br.com.lucaophp.learnenglish;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SpeechToText stt;
    public static Context context;
    private boolean firstTime = true;
    public static TextView subtitle;

    public TextView getSubtitle() {
        return subtitle;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public static Context getContext() {
        return context;
    }

    public void setSST() {
        stt = new SpeechToText();
        setFirstTime(false);
    }

    public static void setsubtitle(String txt){

        subtitle.setText(txt);

    }


    public String[] license(){
        return new String[]{"android.permission.RECORD_AUDIO", "android.permission.INTERNET"};
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23 && (this.checkSelfPermission("android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED)){
            this.requestPermissions(license(), 0);
        }

        subtitle = (TextView) findViewById(R.id.txtCont);

        subtitle.setText("sdasd");
        context = getApplicationContext();
        if (isFirstTime())
            setSST();
    }


    public void onClickEscutar(View view) {
        Log.i("Text","clicou");
        stt.backgroundVoiceListener.run();
    }
}