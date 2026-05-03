package com.example.money_3;

import static com.example.money_3.DatabaseHelper.tb_name;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.money_3.R;

public class addNew extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, RadioGroup.OnCheckedChangeListener {
    private DatabaseHelper dbHelper;
    SQLiteDatabase db;
    int year, month, day;
    int total_income, total_expenditure;
    String type;
    int pay=0;
    int amount;
    Spinner spn_year, spn_month, spn_day, spn_type;
    RadioGroup rdo_pay;
    EditText edt_amount;
    Toast tos;
    static final String [] FROM = {"date","type","amount"};
    String[] arr_year = {"2024", "2023", "2022"};
    String[] arr_month = {"1","2","3","4","5","6","7","8","9","10","11","12"};
    String[] arr_day_even = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
    String[] arr_day_odd = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    String[] arr_day_feb = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28"};
    String[] arr_day_feb_leap = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29"};
    String[] arr_day = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
    String[] arr_income = {"薪水", "零用錢", "利息", "投資","其他"};
    String[] arr_expenditure = {"食物", "飲料", "交通", "衣服", "家用", "旅行", "學習", "社交", "運動", "娛樂", "美容", "其他"};
    String[] arr_type = {"薪水", "零用錢", "利息", "投資","其他"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        //input
        edt_amount = (EditText) findViewById(R.id.edt_amount);
        //spinner setting
        spn_year = (Spinner) findViewById(R.id.spn_year);
        spn_month = (Spinner) findViewById(R.id.spn_month);
        spn_day = (Spinner) findViewById(R.id.spn_day);
        spn_type = (Spinner) findViewById(R.id.spn_type);
        ArrayAdapter<String> adapter_type1= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr_income);
        adapter_type1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_type.setAdapter(adapter_type1);
        spn_type.setTag("spn_type");
        spn_type.setOnItemSelectedListener(this);
        rdo_pay = (RadioGroup) findViewById(R.id.rdo_pay);
        rdo_pay.setOnCheckedChangeListener(this);
        ArrayAdapter<String> adapter_year = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr_year );
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_year.setAdapter(adapter_year);
        spn_year.setTag("spn_year");
        spn_year.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter_month = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr_month );
        adapter_month.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_month.setAdapter(adapter_month);
        spn_month.setTag("spn_month");
        spn_month.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter_day = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr_day);
        adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_day.setAdapter(adapter_day);
        spn_day.setTag("spn_day");
        spn_day.setOnItemSelectedListener(this);

        //DataBase
        dbHelper = new DatabaseHelper(this);
        db = DatabaseManager.getDatabase(this);
    }
    public void goMain (View v) {
        Intent intent = new Intent(addNew.this, com.example.money_3.MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getTag().equals("spn_year"))
            year = Integer.valueOf(arr_year[position]);
        if(parent.getTag().equals("spn_month")) {
            month = Integer.valueOf(arr_month[position]);
            int checkMonth = month % 2;
            if(month==2) {
                if (year % 4 == 0)
                    arr_day = arr_day_feb_leap;
                else
                    arr_day = arr_day_feb;
            }
            else if(month<=7 && month!=2) {
                if (checkMonth == 0)
                    arr_day = arr_day_even;
                else
                    arr_day = arr_day_odd;
            }
            else if(month>7 && month!=2) {
                if (checkMonth == 1)
                    arr_day = arr_day_even;
                else
                    arr_day = arr_day_odd;
            }
            ArrayAdapter<String> adapter_day = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr_day);
            adapter_day.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_day.setAdapter(adapter_day);
            spn_day.setTag("spn_day");
            adapter_day.notifyDataSetChanged();
        }

        if(parent.getTag().equals("spn_day"))
            day = Integer.valueOf(arr_day[position]);
        if(parent.getTag().equals("spn_type"))
            type = String.valueOf(arr_type[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (rdo_pay.getCheckedRadioButtonId() == R.id.rdo_income) {
            arr_type = arr_income;
            pay = 0;
        }
        if (rdo_pay.getCheckedRadioButtonId() == R.id.rdo_expenditure) {
            arr_type = arr_expenditure;
            pay = 1;
        }
        //change the list
        ArrayAdapter<String> adapter_type= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arr_type);
        adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_type.setAdapter(adapter_type);
        spn_type.setTag("spn_type");
        adapter_type.notifyDataSetChanged();
    }
    private void addData(String date, String type, String amount) {
        db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues(3);
        cv.put(FROM[0], date);
        cv.put(FROM[1], type);
        cv.put(FROM[2], amount);
        db.insert(tb_name,null, cv);
    }
    public void cal(View v) {
        String amount_Str = edt_amount.getText().toString();
        String warning = getString(R.string.warning);
        String added = getString(R.string.added);
        String dollar = getString(R.string.dollar);
        if (amount_Str.equals("")) {
            Toast.makeText(this,warning, Toast.LENGTH_SHORT).show();
        }
        else {
            amount = Integer.valueOf(amount_Str);
            if (pay == 1) {
                total_expenditure = total_expenditure + amount;
                amount = amount * (-1);
            } else if (pay == 0) {
                total_income = total_income + amount;
            }
            amount_Str = String.valueOf(amount);
            String date = new String(String.valueOf(year) + "/" + String.valueOf(month) + "/" + String.valueOf(day));
            addData(date, type, amount_Str);
            db.close();
            tos = Toast.makeText(this, added+": " + date + " " + type + " " + amount +" " +dollar, Toast.LENGTH_SHORT);
            tos.show();
            edt_amount.setText("");
        }
    }
}