package com.probot.miniprojecttry1;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.content.Intent;
import org.mariuszgromada.math.mxparser.*;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView txt;
    private TextView ans;
    private TextToSpeech textToSpeech;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt=findViewById(R.id.result);
        ans=findViewById(R.id.ans);

        /*textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.GERMAN);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");
                    } else {
                        //mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });*/

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener(){
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    //Toast.makeText(getBaseContext(),"Success",Toast.LENGTH_LONG).show();
                }
            }
        });
        textToSpeech.setLanguage(Locale.ENGLISH);
    }
   /* private void speak(String result) {
        String text = result;
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }*/

    public void getspeech(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    txt.setText(result.get(0));

                    // Modification
                   // speak(result.get(0));

                }
                break;
        }

        String s = txt.getText().toString();
        Expression exe = new Expression(s);
        String res = String.valueOf(exe.calculate());
        ans.setText(res);
        textToSpeech.speak(res,TextToSpeech.QUEUE_FLUSH,null);
    }
}