package com.appdev.alex.grocerylistapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class GroceryDBHelper extends SQLiteOpenHelper {

    public static final String DATABBASE_NAME = "grocerylist.db";
    public static final int DATABASE_version = 1;

    public GroceryDBHelper( @Nullable Context context) {
        super(context, DATABBASE_NAME, null, DATABASE_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQLITE_CREATE_GROCERYLIST_TABLE = "CREATE TABLE " + GroceryContract.GroceryEntry.TABLE_NAME + " (" +
                GroceryContract.GroceryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + GroceryContract.GroceryEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + GroceryContract.GroceryEntry.COLUM_AMOUNT + " INTEGER NOT NULL, " +
                GroceryContract.GroceryEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQLITE_CREATE_GROCERYLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + GroceryContract.GroceryEntry.TABLE_NAME);
        onCreate(db);

    }
}
