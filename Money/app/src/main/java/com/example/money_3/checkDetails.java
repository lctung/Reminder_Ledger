package com.example.money_3;

import static com.example.money_3.DatabaseHelper.tb_name;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class checkDetails extends AppCompatActivity
        implements AdapterView.OnItemLongClickListener {
    ListView lv_details;
    SimpleCursorAdapter adapter_details;
    Cursor cur;
    SQLiteDatabase db;
    private DatabaseHelper dbHelper;
    Toast tos;
    String [] list = {"date", "type", "amount"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_details);
        lv_details = (ListView) findViewById(R.id.lv_details);
        dbHelper = new DatabaseHelper(this);
        db = DatabaseManager.getDatabase(this);
        cur = db.rawQuery("SELECT * FROM "+tb_name, null);
        adapter_details = new SimpleCursorAdapter(this, R.layout.details_layout,
                cur,list, new int[]{R.id.date, R.id.type, R.id.amount},0);
        lv_details.setAdapter(adapter_details);
        adapter_details.notifyDataSetChanged();
        lv_details.setOnItemLongClickListener(this);
    }

    public void goMain (View v) {
        Intent intent = new Intent(checkDetails.this, com.example.money_3.MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        db = dbHelper.getWritableDatabase();
        cur = db.rawQuery("SELECT * FROM "+tb_name, null);
        cur.moveToPosition(position);
        db.delete(tb_name, "_id="+cur.getInt(0), null);
        String dollar = getString(R.string.dollar);
        String show = cur.getString(1)+" "+cur.getString(2)+cur.getString(3) + " " + dollar;
        String deleted = getString(R.string.deleted);
        tos = Toast.makeText(this,deleted + " " + show, Toast.LENGTH_SHORT);
        tos.show();
        cur = db.rawQuery("SELECT * FROM "+tb_name, null);
        adapter_details = new SimpleCursorAdapter(this, R.layout.details_layout,
                cur,list, new int[]{R.id.date, R.id.type, R.id.amount},0);
        lv_details.setAdapter(adapter_details);
        adapter_details.notifyDataSetChanged();
        db.close();
        return false;
    }
}