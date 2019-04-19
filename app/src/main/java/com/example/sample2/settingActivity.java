package com.example.sample2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import com.example.sample2.PickerView;

public class settingActivity extends AppCompatActivity {
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingl);
        Switch mSwitch1 = findViewById(R.id.switch1);
        mSwitch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    data.backmusic.stopBackgroundMusic();
                }else {
                    data.backmusic.rewindBackgroundMusic();
                }
            }
        });
        Button clear_button = findViewById(R.id.clearbutton);
        clear_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                clearFile(data.filename);
                AlertDialog alertDialog1 = new AlertDialog.Builder(settingActivity.this)
                        .setTitle("Result")//标题
                        .setMessage("clear success!")//内容
                        .setIcon(R.mipmap.ic_launcher)//图标
                        .create();
                alertDialog1.show();
            }
        });
        Button rbutton = findViewById(R.id.rbutton);
        rbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent ( settingActivity.this, MainActivity.class);
                //startActivity ( intent );
                setResult(data.score, intent);
                finish();
            }
        });
        PickerView mview = findViewById(R.id.mview);
        List<String> mdata = new ArrayList<String>();
        for (int i = 10; i < 60; i++)
        {
            mdata.add("" + i);
        }
        mview.setData(mdata);
        mview.setOnSelectListener(new PickerView.onSelectListener()
        {

            @Override
            public void onSelect(String text)
            {
                data.translate_v = Integer.valueOf(text).intValue();
            }
        });
    }
    public void clearFile(String filename){
        FileOutputStream outputStream;
        String msg = "";
        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(msg.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
