package com.example.money_3;

import static com.example.money_3.DatabaseHelper.tb_name;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListResourceBundle;

public class myReport extends AppCompatActivity {
    Cursor cur;
    SQLiteDatabase db;
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_report);
        pieChart = findViewById(R.id.pieChart2);
        String[] type_set = {"食物", "飲料", "交通", "衣服", "家用", "旅行", "學習", "社交", "運動", "娛樂", "美容", "其他"};
        int[] amount_cnt = {0,0,0,0,0,0,0,0,0,0,0,0};
        String type, amount;
        int index=0;
        int target_index=0;
        db = DatabaseManager.getDatabase(this);
        cur = db.rawQuery("SELECT * FROM "+tb_name, null);
        if (cur.moveToFirst()) {
            do {
                index = 0;
                type = cur.getString(2);
                amount = cur.getString(3);
                while (index < type_set.length) {
                    if (type.equals(type_set[index])) {
                        target_index = index;
                        break;
                    }
                    else
                        index++;
                }
                int money = Integer.valueOf(amount);
                if (money<0) money = -money;
                amount_cnt[target_index] = money + amount_cnt[target_index];
            } while (cur.moveToNext ());
        }
//        txv.setText(Arrays.toString(amount_cnt));
        List<PieEntry> entries = new ArrayList<>();
        for (int i=0; i < type_set.length ; i++) {
            entries.add(new PieEntry(amount_cnt[i],type_set[i]));
        }
        PieDataSet dataSet = new PieDataSet(entries,null);
        PieData data = new PieData(dataSet);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.orange));
        colors.add(getResources().getColor(R.color.red_1));
        colors.add(getResources().getColor(R.color.pink));
        colors.add(getResources().getColor(R.color.golden));
        colors.add(getResources().getColor(R.color.brown_1));
        colors.add(getResources().getColor(R.color.darkGreen));
        colors.add(getResources().getColor(R.color.blue));
        colors.add(getResources().getColor(R.color.grassGreen));
        colors.add(getResources().getColor(R.color.red_3));
        colors.add(getResources().getColor(R.color.Green));
        colors.add(getResources().getColor(R.color.warmGreen));
        colors.add(getResources().getColor(R.color.brown));
        data.setDrawValues(false);
//        data.setValueTextSize(16f);
//        data.setValueTextColor(Color.WHITE);
        pieChart.setDrawHoleEnabled(false); //關閉內圓
        pieChart.getDescription().setEnabled(false); //關閉圖表描述
        pieChart.setRotationEnabled(false); //關閉手動旋轉
        dataSet.setColors(colors);
        pieChart.setData(data);
        pieChart.invalidate(); //refresh chart
    }
    public void goMain (View v) {
        Intent intent = new Intent(myReport.this, MainActivity.class);
        startActivity(intent);
//        finish();
    }
}