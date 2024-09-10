package com.example.money_3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String db_name = "moneyDB_2";
    public static final String tb_name = "moneyTB_2";
    private static final int db_version = 1;
    private static DatabaseHelper instance;
    public DatabaseHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String myMoneyTable = "CREATE TABLE IF NOT EXISTS "
                + tb_name + "(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "date VARCHAR (32)," + "type VARCHAR (32)," + "amount VARCHAR (16))";
        db.execSQL(myMoneyTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void clearTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(tb_name, null, null);
        db.close();
    }
}
