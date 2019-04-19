package com.example.sample2;

import android.media.AudioManager;
import android.media.SoundPool;
import android.opengl.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import com.example.sample2.mySurfaceView;
class point{
    int x,y;
    public point(int xx,int yy){
        x = xx;
        y = yy;
    }
}
/*class point3d{
    float x,y,z;
    public point3d(float x,float y,float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }*/
    class point3d implements Cloneable{
        float x,y,z;
        public float[] tomat(float[] ma){
            float[] m = new float[]{
                    x,y,z,1,
                    0,0,0,0,
                    0,0,0,0,
                    0,0,0,0
            };
            Matrix.multiplyMM(m, 0, ma, 0, m, 0);
            return m;
        }
        public point3d(float x,float y,float z){
            this.x = x;
            this.y = y;
            this.z = z;
        }
        @Override
        public Object clone(){
            point3d p = null;
            try{
                p = (point3d)super.clone();
            }catch (CloneNotSupportedException e){
                e.printStackTrace();
            }
            return p;
        }
    }

class myrec implements Cloneable{
    point3d p1, p2, p3, p4;
    int h;
    public myrec(point3d p1, point3d p2, point3d p3, point3d p4, int h) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.h = h;
    }
    @Override
    public Object clone(){
        myrec p = null;
        try{
            p = (myrec)super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        p.p1 = (point3d) p1.clone();
        p.p2 = (point3d) p2.clone();
        p.p3 = (point3d) p3.clone();
        p.p4 = (point3d) p4.clone();

        return p;
    }
}
class myrec2 implements Cloneable{
    point3d[] p = new point3d[9];
    public myrec2(point3d p1, point3d p2, point3d p3, point3d p4, point3d p5,point3d p6,point3d p7,point3d p8) {
        this.p[1] = p1;
        this.p[2] = p2;
        this.p[3] = p3;
        this.p[4] = p4;
        this.p[5] = p5;
        this.p[6] = p6;
        this.p[7] = p7;
        this.p[8] = p8;
    }
    public point3d tom(point3d p ,float[] ma){
        point3d p1 = new point3d(p.tomat(ma)[0],p.tomat(ma)[1],p.tomat(ma)[2]);
        return p1;
    }
    public void toM(float[] ma){
        this.p[1] = tom(p[1],ma);
        this.p[2] = tom(p[2],ma);
        this.p[3] = tom(p[3],ma);
        this.p[4] = tom(p[4],ma);
        this.p[5] = tom(p[5],ma);
        this.p[6] = tom(p[6],ma);
        this.p[7] = tom(p[7],ma);
        this.p[8] = tom(p[8],ma);
    }
    public myrec2(){
        p[1]= (point3d)data.current_rec.p1.clone();
        p[2]= (point3d)data.current_rec.p2.clone();
        p[3]= (point3d)data.current_rec.p3.clone();
        p[4]= (point3d)data.current_rec.p4.clone();
        p[5]= (point3d)data.current_rec.p1.clone();
        p[6]= (point3d)data.current_rec.p2.clone();
        p[7]= (point3d)data.current_rec.p3.clone();
        p[8]= (point3d)data.current_rec.p4.clone();
        p[5].y -= data.current_rec.h;
        p[6].y -= data.current_rec.h;
        p[7].y -= data.current_rec.h;
        p[8].y -= data.current_rec.h;
    }
    @Override
    public Object clone(){
        myrec2 pp = null;
        try{
            pp = (myrec2)super.clone();
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        pp.p = p.clone();
        //Log.e("address1","rec1 "+this.p[5]+" tmp "+pp.p[5]);
        pp.p[1] = new point3d(p[1].x,p[1].y,p[1].z);
        pp.p[2] = new point3d(p[2].x,p[2].y,p[2].z);
        pp.p[3] = new point3d(p[3].x,p[3].y,p[3].z);
        pp.p[4] = new point3d(p[4].x,p[4].y,p[4].z);
        pp.p[5] = new point3d(p[5].x,p[5].y,p[5].z);
        pp.p[6] = new point3d(p[6].x,p[6].y,p[6].z);
        pp.p[7] = new point3d(p[7].x,p[7].y,p[7].z);
        pp.p[8] = new point3d(p[8].x,p[8].y,p[8].z);
           // Log.e("address","rec1 "+this.p[5]+" tmp "+pp.p[5]);

        return pp;
    }
}
public class data{
    static int screenWidth;
    static int screenHeight;
    static int H = 30;
    static myrec current_rec;
    static int initW = 300;
    static ArrayList<myrec> queue = new ArrayList<myrec>();
    static point3d initpoint;
    static int current = 0;
    static int current_h = 0;
    static int max_h = 0;
    static int is_stop = 0;
    static int is_x = 1;
    static myrec2 dynamic_rec1 ;
    static float cangle = 0,cshift = 0,dshift = 0;
    static int flag;
    static int score = 0;
    static int[] scorelist = new int[110];
    static int scorecount = 0;
    static String filename = "score.data";
    static int translate_v = 25;
    static BackgroundMusic backmusic;
    static int soundnumber = 0;
    static float eps = 1;
    static SoundPool mSound = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    static int counts = 10;
    static boolean isfirst = true;
}


