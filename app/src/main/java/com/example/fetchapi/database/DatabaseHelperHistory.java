package com.example.fetchapi.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.fetchapi.model.History;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelperHistory extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "historyDatabase";
    private static String TABLE_NAME = "historyTable";
    private static String COLUMN_ID = "id";
    private static String COLUMN_NAME = "name";
    private static String COLUMN_BRAND = "brand";
    private static String COLUMN_CATEGORY = "category";
    private static String COLUMN_QUANTITY = "quantity";
    private static String COLUMN_ORDER_ID = "order_id";
    public DatabaseHelperHistory(Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ORDER_ID + " INTEGER, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_BRAND + " TEXT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_QUANTITY + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertHistory(History history) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ORDER_ID, history.getOrderId());
        values.put(COLUMN_NAME, history.getName());
        values.put(COLUMN_BRAND, history.getBrand());
        values.put(COLUMN_CATEGORY, history.getCategory());
        values.put(COLUMN_QUANTITY, history.getQuantity());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

//    public List<History> getAllHistory() {
//        List<History> list = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY id DESC", null);
//        if (cursor.moveToFirst()) {
//            do {
//                History h = new History();
//                h.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
//                h.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
//                h.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND)));
//                h.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
//                h.setQuantity(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
//                list.add(h);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }


    public Map<Integer, List<History>> getHistory() {
        Map<Integer, List<History>> grouped = new LinkedHashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY order_id DESC, id ASC", null);

        if (cursor.moveToFirst()) {
            do {
                History h = new History();
                int orderId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ORDER_ID));
                h.setOrderId(orderId);
                h.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                h.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND)));
                h.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                h.setQuantity(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));

                if (!grouped.containsKey(orderId)) {
                    grouped.put(orderId, new ArrayList<>());
                }
                grouped.get(orderId).add(h);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return grouped;
    }


}
