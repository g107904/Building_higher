package com.example.sample2;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.util.Xml;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParser;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class drawActivity extends AppCompatActivity {
    public static Button b;
    public static TextView text;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        mySurfaceView msurfaceView = new mySurfaceView(this);
        //Log.e("run:"," c");
        setContentView(msurfaceView);
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        /*LinearLayout ll = new LinearLayout(this);
        Button b = new Button(this);
        b.setText("hello world");
        ll.addView(b);
        ll.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        this.addContentView(ll,
                new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));*/
        data.current = 0;
        data.current_h = 0;
        data.max_h = 0;
        data.is_stop = 0;
        data.is_x = 1;
        data.cangle = 0;
        data.cshift = 0;
        data.dshift = 0;
        data.score = 0;
        data.queue.clear();
        initmusic();
        LinearLayout ll = new LinearLayout(this);
        b = new Button(this);
        b.setText("Resume");
        ll.addView(b);
        ll.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        this.addContentView(ll,
                new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        b.setVisibility(View.GONE);
        LinearLayout lr = new LinearLayout(this);
        text = new TextView(this);
        text.setTextSize(TypedValue.COMPLEX_UNIT_SP,42);
        text.setText(String.valueOf(data.score));
        text.setTextColor(Color.WHITE);
        lr.addView(text);
        lr.setGravity(Gravity.CENTER_HORIZONTAL);
        this.addContentView(lr,
                new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        data.screenWidth = display.widthPixels;
        data.screenHeight = display.heightPixels-200;
        float t = (float)data.initW / (float)data.screenWidth;
        point3d p1 = new point3d(data.initW/2,-data.screenHeight/2,t);
        point3d p2 = new point3d(data.initW/2,-data.screenHeight/2,-t);
        point3d p3 = new point3d(-data.initW/2,-data.screenHeight/2,t);
        point3d p4 = new point3d(-data.initW/2,-data.screenHeight/2,-t);
        myrec rec1 = new myrec(p1,p2,p3,p4,data.H);
        data.queue.add(rec1);
        point3d p11 = new point3d(data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/4*3,t);
        point3d p12 = new point3d(data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/4*3,-t);
        point3d p13 = new point3d(-data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/4*3,t);
        point3d p14 = new point3d(-data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/4*3,-t);
        myrec rec2 = new myrec(p11,p12,p13,p14,(data.screenHeight/2-data.initW-data.H )/4 );
        data.queue.add(rec2);
        point3d p31 = new point3d(data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/2,t);
        point3d p32 = new point3d(data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/2,-t);
        point3d p33 = new point3d(-data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/2,t);
        point3d p34 = new point3d(-data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/2,-t);
        myrec rec32 = new myrec(p31,p32,p33,p34,(data.screenHeight/2-data.initW-data.H )/4 );
        data.queue.add(rec32);
        point3d p41 = new point3d(data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/4,t);
        point3d p42 = new point3d(data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/4,-t);
        point3d p43 = new point3d(-data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/4,t);
        point3d p44 = new point3d(-data.initW/2,-data.initW-data.H-(data.screenHeight/2-data.initW-data.H )/4,-t);
        myrec rec42 = new myrec(p41,p42,p43,p44,(data.screenHeight/2-data.initW-data.H )/4 );
        data.queue.add(rec42);
        point3d p111 = new point3d(data.initW/2,-data.initW-data.H,t);
        point3d p112 = new point3d(data.initW/2,-data.initW-data.H,-t);
        point3d p113 = new point3d(-data.initW/2,-data.initW-data.H,t);
        point3d p114 = new point3d(-data.initW/2,-data.initW-data.H,-t);
        myrec rec12 = new myrec(p111,p112,p113,p114,(data.screenHeight/2-data.initW-data.H )/4 );
        data.queue.add(rec12);
        point3d p21 = new point3d(data.initW/2,-data.initW,t);
        point3d p22 = new point3d(data.initW/2,-data.initW,-t);
        point3d p23 = new point3d(-data.initW/2,-data.initW,t);
        point3d p24 = new point3d(-data.initW/2,-data.initW,-t);
        myrec rec3 = new myrec(p21,p22,p23,p24,data.H);
        data.queue.add(rec3);
        data.current_rec = (myrec) rec3.clone();
        data.current_rec.p1.y += rec3.h;
        data.current_rec.p2.y += rec3.h;
        data.current_rec.p3.y += rec3.h;
        data.current_rec.p4.y += rec3.h;
        data.max_h = 0;
        b.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //clearFile(data.filename);
                readSaveFile(data.filename);
                data.scorecount++;
                savePackageFile(data.filename);
                Intent intent = new Intent ( drawActivity.this, MainActivity.class);
                //startActivity ( intent );
                setResult(data.score, intent);
                finish();
            }
        } );
        //Log.e("run2:"," c");
    }
    /*public boolean onTouchEvent(MotionEvent e){

        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                Intent intent = new Intent ( drawActivity.this, MainActivity.class);
                startActivity ( intent );
        }
        return true;
    }*/
    public void readSaveFile(String filename) {
        FileInputStream inputStream;
        String str = null;
        try {
            inputStream = openFileInput(filename);
            byte temp[] = new byte[1024];
            StringBuilder sb = new StringBuilder();
            int len = 0;
            while ((len = inputStream.read(temp)) > 0){
                sb.append(new String(temp, 0, len));
            }
            //Log.d("msg", "readSaveFile: \n" + sb.toString());
            str = sb.toString();
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        int count = 0,num = 0,flag = 0;
        for(int i = 0;str != null && i < str.length();i++){
            if(str.charAt(i) >= '0' && str.charAt(i) <= '9'){
                num = num * 10 + str.charAt(i)-'0';
            }
            else{
                if(flag == 0){
                    data.scorecount = num;
                    flag = 1;
                }
                else{
                    data.scorelist[count] = num;
                    count++;
                }
                num = 0;
            }
        }
    }
    public  void savePackageFile(String filename) {
        String msg = "";
        msg  = msg+data.scorecount+' ';
        if(data.scorecount == 100){
            for(int i = 0;i < data.scorecount-1;i++){
                data.scorelist[i] = data.scorelist[i+1];
            }

        }
        data.scorelist[data.scorecount-1] = data.score;
        for(int i = 0;i < data.scorecount;i++){
            msg = msg + data.scorelist[i]+' ';
        }
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(msg.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void initmusic(){
        data.mSound.load(this,R.raw.doo,1);
        data.mSound.load(this,R.raw.re,1);
        data.mSound.load(this,R.raw.mi,1);
        data.mSound.load(this,R.raw.fa,1);
        data.mSound.load(this,R.raw.sol,1);
        data.mSound.load(this,R.raw.la,1);
        data.mSound.load(this,R.raw.si,1);
    }

}
