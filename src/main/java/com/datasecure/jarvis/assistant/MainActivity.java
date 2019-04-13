package com.datasecure.jarvis.assistant;

import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText e1;
    private TextToSpeech tts;
    private Button btn;
    private SeekBar sb1;
    private SeekBar sb2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btn);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                   int res = tts.setLanguage(Locale.ENGLISH);
                   if (res==TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED)
                   {
                       Log.e("TTS","not supported");
                   }
                    else{
                       btn.setEnabled(true);
                   }
                }
                else {
                    Log.e("TTS","failed to initialize");
                }
            }
        });
        e1 = (EditText) findViewById(R.id.e1);
        sb1 = (SeekBar) findViewById(R.id.sb1);
        sb2 = (SeekBar) findViewById(R.id.sb2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }
        });

    }

    private void speak() {
        String s = e1.getText().toString();
        float pitch = (float) sb1.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;
        float speed = (float) sb2.getProgress() / 50;
        if (speed < 0.1) speed = 0.1f;
        tts.setPitch(pitch);
        tts.setSpeechRate(speed);
        tts.speak(s, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if(tts!=null){
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }
}
