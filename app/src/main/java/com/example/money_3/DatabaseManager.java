package com.example.money_3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager {
    private static DatabaseHelper dbHelper;
    private static SQLiteDatabase database;

    private DatabaseManager() {

    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context.getApplicationContext());
        }
        return dbHelper;
    }

    public static synchronized SQLiteDatabase getDatabase(Context context) {
        if (database == null) {
            dbHelper = getInstance(context);
            database = dbHelper.getWritableDatabase();
        }
        return database;
    }

    public static synchronized void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
        }
    }
}
