package com.example.sample2;

import android.content.Context;
//import android.opengl.EGLConfig;
import android.content.Intent;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import javax.microedition.khronos.opengles.GL10;

public class mySurfaceView extends GLSurfaceView {
    private SceneRenderer mRenderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f/320;
    private float mPreviousY;
    private float mPreviousX;
    static float yAngle = 0,xAngle = 0,dxx=-1f;
    translateThread T1;
    boolean dynamic_t = false;
    public mySurfaceView(Context context){
        super(context);
        Log.e("run","    surf");
        this.setEGLContextClientVersion(2);
        mRenderer = new SceneRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
    public mySurfaceView(Context context, AttributeSet attrs) {

        super(context, attrs );
        this.setEGLContextClientVersion(2);
        mRenderer = new SceneRenderer();
        setRenderer(mRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        Log.e("run","    surf");
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        float y = e.getY();
        float x = e.getX();
        switch(e.getAction()){
            case MotionEvent.ACTION_MOVE:
                float dy = y - mPreviousY;
                float dx = x - mPreviousX;
                /*for(Heaxgon h : mRenderer.ha){
                    h.yAngle += dx * TOUCH_SCALE_FACTOR;
                    h.xAngle += dy * TOUCH_SCALE_FACTOR;
                }*/
                yAngle += dx * TOUCH_SCALE_FACTOR;
                xAngle += dy * TOUCH_SCALE_FACTOR;
            case MotionEvent.ACTION_DOWN:
                if(data.is_stop == 1)
                    return false;
                T1.flag = false;
                T1.interrupt();
                myrec rec2 = (myrec)data.current_rec.clone();
                /*rec2.p1.y += rec2.h;
                rec2.p2.y += rec2.h;
                rec2.p3.y += rec2.h;
                rec2.p4.y += rec2.h;*/
                if(data.is_x == 1) {
                    float x1 = rec2.p3.x, x2 = rec2.p2.x, x1c = x1 + dxx * data.screenWidth / 2, x2c = x2 + dxx * data.screenWidth / 2;
                    if (x2c <= x1 || x1c >= x2) {
                        data.is_stop = 1;
                        data.current_h = 0;
                        data.current = 0;
                        drawActivity.b.setVisibility(View.VISIBLE );
                        //todo
                        return true;
                    } else if (x2c <= x2) {
                        rec2.p1.x = rec2.p2.x = x2c;
                        data.dynamic_rec1 = new myrec2();
                        data.dynamic_rec1.p[3].x = data.dynamic_rec1.p[4].x = data.dynamic_rec1.p[7].x = data.dynamic_rec1.p[8].x = x1c;
                        data.dynamic_rec1.p[1].x = data.dynamic_rec1.p[2].x = data.dynamic_rec1.p[5].x = data.dynamic_rec1.p[6].x = x1;
                        data.flag = -1;
                        myrec rec1 = (myrec) rec2.clone();
                        data.queue.add(rec1);
                        rec2.p1.y += rec2.h;
                        rec2.p2.y += rec2.h;
                        rec2.p3.y += rec2.h;
                        rec2.p4.y += rec2.h;
                        data.current_rec = (myrec) rec2.clone();
                        //data.current_rec = rec2;
                        //todo
                    } else if (x1c >= x1) {
                        rec2.p3.x = rec2.p4.x = x1c;
                        data.dynamic_rec1 = new myrec2();
                        data.dynamic_rec1.p[3].x = data.dynamic_rec1.p[4].x = data.dynamic_rec1.p[7].x = data.dynamic_rec1.p[8].x = x2;
                        data.dynamic_rec1.p[1].x = data.dynamic_rec1.p[2].x = data.dynamic_rec1.p[5].x = data.dynamic_rec1.p[6].x = x2c;
                        data.flag = 1;
                        myrec rec1 = (myrec) rec2.clone();
                        data.queue.add(rec1);
                        rec2.p1.y += rec2.h;
                        rec2.p2.y += rec2.h;
                        rec2.p3.y += rec2.h;
                        rec2.p4.y += rec2.h;
                        data.current_rec = (myrec) rec2.clone();
                        // data.current_rec = rec2;
                    }

                }
                else{
                    float z1 = rec2.p2.z,z2 = rec2.p1.z,z1c = z1+dxx,z2c = z2+dxx;
                    if (z2c <= z1 || z1c >= z2) {
                        data.is_stop = 1;
                        data.current_h = 0;
                        data.current = 0;
                        drawActivity.b.setVisibility(View.VISIBLE );
                        //todo
                        return true;
                    } else if (z2c <= z2) {
                        rec2.p1.z = rec2.p3.z = z2c;
                        data.dynamic_rec1 = new myrec2();
                        data.dynamic_rec1.p[2].z = data.dynamic_rec1.p[4].z = data.dynamic_rec1.p[6].z = data.dynamic_rec1.p[8].z = z1c;
                        data.dynamic_rec1.p[1].z = data.dynamic_rec1.p[3].z = data.dynamic_rec1.p[5].z = data.dynamic_rec1.p[7].z = z1;
                        data.flag = -1;
                        myrec rec1 = (myrec) rec2.clone();
                        data.queue.add(rec1);
                        rec2.p1.y += rec2.h;
                        rec2.p2.y += rec2.h;
                        rec2.p3.y += rec2.h;
                        rec2.p4.y += rec2.h;
                        data.current_rec = (myrec) rec2.clone();
                        //data.current_rec = rec2;
                        //todo
                    } else if (z1c >= z1) {
                        rec2.p2.z = rec2.p4.z = z1c;
                        data.dynamic_rec1 = new myrec2();
                        data.dynamic_rec1.p[2].z = data.dynamic_rec1.p[4].z = data.dynamic_rec1.p[6].z = data.dynamic_rec1.p[8].z = z2;
                        data.dynamic_rec1.p[1].z = data.dynamic_rec1.p[3].z = data.dynamic_rec1.p[5].z = data.dynamic_rec1.p[7].z = z2c;
                        data.flag = 1;
                        myrec rec1 = (myrec) rec2.clone();
                        data.queue.add(rec1);
                        rec2.p1.y += rec2.h;
                        rec2.p2.y += rec2.h;
                        rec2.p3.y += rec2.h;
                        rec2.p4.y += rec2.h;
                        data.current_rec = (myrec) rec2.clone();
                        // data.current_rec = rec2;
                    }
                }
                if (rec2.p1.y - data.current_h >= data.max_h) {
                    data.current_h += data.queue.get(data.current).h;
                    data.current++;
                }
                data.score = data.queue.size()-6;
                if(data.score < 0)
                    data.score = 0;
                drawActivity.text.setText(String.valueOf(data.score));
                dynamic_t = true;
                dynamicThread T2 = new dynamicThread();
                T2.start();
                Log.e("touch","x "+data.dynamic_rec1.p[5].x);
                //T1.start();

        }
        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
    public class SceneRenderer implements GLSurfaceView.Renderer{
        Heaxgon[] ha = new Heaxgon[6];
        public void onDrawFrame(GL10 gl){
            GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT|GLES20.GL_COLOR_BUFFER_BIT);
            if(T1.flag) {
                Cubee c = new Cubee(mySurfaceView.this, -1);
                c.drawself();
                //myrec rec2 = data.current_rec;
                

            }
            for(int i = data.current;i < data.queue.size();i++){
                Cube t = new Cube(mySurfaceView.this,i);
                t.drawself();
            }
            if(dynamic_t){
                Log.e("draw","    cshift"+data.cshift+"  dshift"+data.dshift+" cangle"+data.cangle);
                dynamic_Cube dyc = new dynamic_Cube(mySurfaceView.this);
                dyc.drawself();
            }
            /*for(Heaxgon h : ha){
                h.drawSelf();
            }*/
        }

        public void onSurfaceCreated(GL10 gl, EGLConfig eglConfig) {
            Log.e("run",":Create");
            GLES20.glClearColor(0.5f,0.5f,0.5f,1.0f);
            for(int i = 0;i < 6;i++) {
                ha[i] = new Heaxgon(mySurfaceView.this, -(float) i / 2);
            }
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            T1 = new translateThread();

            T1.start();
        }

        public void onSurfaceChanged(GL10 gl, int width, int height){
            GLES20.glViewport(0,0,width,height);

            float ratio =(float)width / height;
            //Matrix.frustumM(Heaxgon.mProjMatrix,0,-ratio,ratio,-1,1,-1,10);
        }

    }
    public class translateThread extends Thread{
        public boolean flag = false;
        public  float f= 1f;

        @Override
        public void run(){
            flag = true;
            while(flag){

                dxx += f*0.02;
                if(dxx < -1.1f || (Math.abs(dxx - 1f) < 1e-6 && f > 0)|| (Math.abs(dxx+1f)<1e-6 && f < 0)) {
                    f = -f;
                    //xAngle += 50 * TOUCH_SCALE_FACTOR;
                }
                try{
                    Thread.sleep(25);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }

        }
    }
    public class dynamicThread extends Thread{
        public boolean flag = false;

        @Override
        public void run(){
            while(data.cshift-data.dynamic_rec1.p[5].y <= data.screenHeight/2){
                data.cangle += -data.flag*5*Math.PI/180.0;
                data.cshift += 5;
                data.dshift += data.flag*2;
                Log.e("run","    cshift"+data.cshift+"  dshift"+data.dshift+" cangle"+data.cangle);

                try{
                    Thread.sleep(25);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            data.cshift = 0;
            data.dshift = 0;
            data.cangle = 0;
            dynamic_t = false;
            data.is_x = -data.is_x;
            dxx = -1f;

            T1 = new translateThread();

            T1.start();
            T1.flag = true;
        }
    }
}
