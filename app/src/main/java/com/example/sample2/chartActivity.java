package com.example.sample2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.components.Description;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;

public class chartActivity extends AppCompatActivity {
    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        Button button1 = findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent ( chartActivity.this, MainActivity.class);
                //startActivity ( intent );
                setResult(data.score, intent);
                finish();
            }
        });
        LineChart mLineChart = findViewById(R.id.lineChart);
        //显示边界
        mLineChart.setDrawBorders(true);
        //设置数据
        List<Entry> entries = new ArrayList<>();
        readSaveFile(data.filename);
        if(data.scorecount == 0){
            AlertDialog alertDialog1 = new AlertDialog.Builder(this)
                    .setTitle("Warning")//标题
                    .setMessage("No data!")//内容
                    .setIcon(R.mipmap.ic_launcher)//图标
                    .create();
            alertDialog1.show();
            return;
        }
        for (int i = 0; i < data.scorecount; i++) {
            entries.add(new Entry(i, data.scorelist[i]));
        }
        //一个LineDataSet就是一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "score");
        LineData data = new LineData(lineDataSet);
        mLineChart.setData(data);

        mLineChart.setBackgroundColor(Color.WHITE);
//是否显示边界
        mLineChart.setDrawBorders(false);
        mLineChart.setDrawGridBackground(false);
        xAxis = mLineChart.getXAxis();
        leftYAxis = mLineChart.getAxisLeft();
        rightYaxis = mLineChart.getAxisRight();
        xAxis.setDrawGridLines(false);
        rightYaxis.setDrawGridLines(false);
        leftYAxis.setDrawGridLines(true);
        leftYAxis.enableGridDashedLine(10f, 10f, 0f);
        rightYaxis.setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(0f);
        rightYaxis.setAxisMinimum(0f);

        /***折线图例 标签 设置***/
        legend = mLineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);

        Description description = new Description();
//        description.setText("需要展示的内容");
        description.setEnabled(false);
        mLineChart.setDescription(description);

    }
    public  void savePackageFile(String filename) {
        String msg = null;
        msg  = msg+data.scorecount+' ';
        if(data.scorecount == 100){
            for(int i = 0;i < data.scorecount-1;i++){
                data.scorelist[i] = data.scorelist[i+1];
            }

        }
        data.scorelist[data.scorecount] = data.score;
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
    public void readSaveFile(String filename) {
        data.scorecount = 0;
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

}
