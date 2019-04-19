package com.example.sample2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static java.lang.Math.*;
import com.example.sample2.data;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(data.isfirst){
            data.backmusic = BackgroundMusic.getInstance(getApplicationContext());
            data.isfirst = false;
        }
        data.backmusic.playBackgroundMusic("backsound.mp3",true);
        Button button = findViewById ( R.id.button1);
        button.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( MainActivity.this, drawActivity.class);
                startActivityForResult ( intent ,1);
            }
        } );
        Button button1 = findViewById(R.id.button2);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent ( MainActivity.this, chartActivity.class);
                startActivityForResult ( intent ,2);
            }
        });
        Button button0 = findViewById(R.id.button4);
        button0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent ( MainActivity.this, settingActivity.class);
                //startActivity ( intent );
                startActivityForResult ( intent ,3);
            }
        });
        File logFile = new File(getFilesDir(), data.filename);
        // Make sure log file is exists
        if (!logFile.exists()) {
            boolean result; // 文件是否创建成功
            try {
                result = logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            if (!result) {
                return;
            }
        }
    }
}
