package com.example.sample2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class light_circle extends Cube {
    private float dx = 100/data.screenWidth;
    public light_circle(mySurfaceView mv,int index){
        initVertexData(index);
        initShader(mv);
    }
    public void initVertexData(int i){
        myrec tmp = (myrec)data.queue.get(i).clone();
        tmp.p1.y -= data.current_h;
        tmp.p2.y -= data.current_h;
        tmp.p3.y -= data.current_h;
        tmp.p4.y -= data.current_h;
        vCount = 8;
        float vertices[] = new float[]{
                2*(tmp.p1.x)/data.screenWidth,2*(tmp.p1.y-tmp.h)/data.screenHeight,tmp.p1.z,
                2*(tmp.p2.x)/data.screenWidth,2*(tmp.p2.y-tmp.h)/data.screenHeight,tmp.p2.z,
                2*(tmp.p3.x)/data.screenWidth,2*(tmp.p3.y-tmp.h)/data.screenHeight,tmp.p3.z,
                2*(tmp.p4.x)/data.screenWidth,2*(tmp.p4.y-tmp.h)/data.screenHeight,tmp.p4.z,
                2*(tmp.p1.x)/data.screenWidth+dx,2*(tmp.p1.y-40)/data.screenHeight,tmp.p1.z+dx,
                2*(tmp.p2.x)/data.screenWidth+dx,2*(tmp.p2.y-40)/data.screenHeight,tmp.p2.z-dx,
                2*(tmp.p3.x)/data.screenWidth-dx,2*(tmp.p3.y-40)/data.screenHeight,tmp.p3.z+dx,
                2*(tmp.p4.x)/data.screenWidth-dx,2*(tmp.p4.y-40)/data.screenHeight,tmp.p4.z-dx
        };
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);
        float colors[] = new float[]{
                1,1,1,1,
                1,1,1,1,
                1,1,1,1,
                1,1,1,1,
                1,1,1,1,
                1,1,1,1,
                1,1,1,1,
                1,1,1,1
        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mColorBuffer = cbb.asFloatBuffer();
        mColorBuffer.put(colors);
        mColorBuffer.position(0);
        iCount = 24;
        byte indices[] = new byte[]{
                0,4,6,0,2,6,
                0,1,5,0,4,5,
                1,3,5,3,5,7,
                2,3,6,3,6,7
        };
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);
        mIndexBuffer.put(indices);
        mIndexBuffer.position(0);
    }
}
