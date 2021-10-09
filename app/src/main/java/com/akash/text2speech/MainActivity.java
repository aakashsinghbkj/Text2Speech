package com.akash.text2speech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextToSpeech tts;
    private EditText write;
    private ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        write= findViewById(R.id.write);
        button= findViewById(R.id.button);
        tts= new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i==TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.US);
                    if(result== TextToSpeech.LANG_MISSING_DATA
                    || result==TextToSpeech.LANG_NOT_SUPPORTED){
                         Toast.makeText(getApplicationContext(), "Oops! Please enter in english", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Intialization failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = write.getText().toString().trim();
                int result = tts.speak(input, TextToSpeech.QUEUE_FLUSH, null);
                if (result==TextToSpeech.ERROR){
                    Toast.makeText(getApplicationContext(), "Error in conversion", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            Window window= this.getWindow();
            window.setStatusBarColor(this.getResources().getColor(R.color.black));

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tts!= null){
            tts.stop();
            tts.shutdown();
        }
    }
}