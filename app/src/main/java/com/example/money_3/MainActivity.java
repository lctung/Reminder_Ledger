package com.example.money_3;

import static com.example.money_3.DatabaseHelper.tb_name;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener {
    TextView txv_expenditure, txv_income;
    String data;
    SQLiteDatabase db;
    Cursor cur;
    PieChart pieChart;
    private DatabaseHelper dbHelper;
    int total_income, total_expenditure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txv_income = (TextView) findViewById(R.id.txv_income);
        txv_expenditure = (TextView) findViewById(R.id.txv_expenditure);
        pieChart = findViewById(R.id.pieChart);

        db = DatabaseManager.getDatabase(this);
        cur = db.rawQuery("SELECT * FROM "+tb_name, null);
        if (cur.moveToFirst()) {
            do {
                int money = Integer.valueOf(cur.getString(3));
                if(money>0) {
                    total_income += money;
                }
                else
                    total_expenditure += money;
            } while (cur.moveToNext ());
        }
        String dollarSign = getString(R.string.dollarSign);
        txv_income.setText(dollarSign + String.valueOf(total_income));
        txv_expenditure.setText(dollarSign + String.valueOf(-total_expenditure));

        String income = getString(R.string.income_total);
        String expenditure = getString(R.string.expenditure_total);
        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(total_income, income));
        entries.add(new PieEntry((-total_expenditure), expenditure));

        PieDataSet dataSet = new PieDataSet(entries,null);
        PieData data = new PieData(dataSet);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(getResources().getColor(R.color.red));
        colors.add(getResources().getColor(R.color.blue));
        data.setDrawValues(true);
        data.setValueTextSize(16f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setDrawHoleEnabled(false); //關閉內圓
        pieChart.getDescription().setEnabled(false); //關閉圖表描述
        pieChart.setRotationEnabled(false); //關閉手動旋轉
        dataSet.setColors(colors);
        pieChart.setData(data);
        pieChart.invalidate(); //refresh chart
    }
    public void goAddNew (View v) {
        Intent intent = new Intent(MainActivity.this, addNew.class);
        startActivity(intent);
    }
    public void goCheckDetails(View v) {
        Intent intent = new Intent(MainActivity.this, checkDetails.class);
        intent.putExtra("database_data",data);
        startActivity(intent);
    }
    public void goMyReport(View v) {
        Intent intent = new Intent(MainActivity.this, myReport.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}