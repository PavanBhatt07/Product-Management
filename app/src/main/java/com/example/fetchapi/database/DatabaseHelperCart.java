package com.example.fetchapi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.fetchapi.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperCart extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cartdatabase.db";
    public static final String TABLE_CART = "cart";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BRAND = "brand";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_QUANTITY = "quantity";

    public DatabaseHelperCart(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CART + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT UNIQUE, " +
                        COLUMN_BRAND + " TEXT, " +
                        COLUMN_CATEGORY + " TEXT, " +
                        COLUMN_QUANTITY + " TEXT" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public void InsertIntoCart(Cart cart){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, cart.getName());
        values.put(COLUMN_BRAND, cart.getBrand());
        values.put(COLUMN_CATEGORY, cart.getCategory());
        values.put(COLUMN_QUANTITY, cart.getQuantity());
        db.insertWithOnConflict(TABLE_CART, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public List<Cart> getAllProductsFromCart(){
        List<Cart> cartList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART, null);
        Log.d("CartQuery", "Total rows in cart table: " + cursor.getCount());
        if(cursor.moveToFirst()){
            do{
                Cart cart = new Cart();
                cart.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                cart.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                cart.setBrand(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BRAND)));
                cart.setCategory(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)));
                cart.setQuantity(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                cartList.add(cart);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartList;
    }
    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }



    public boolean isProductInCart(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CART + " WHERE name = ?", new String[]{productName});
        boolean exists = (cursor != null && cursor.moveToFirst());
        if (cursor != null) cursor.close();
        db.close();
        return exists;
    }

    public void updateQuantity(String productName, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QUANTITY, quantity);
        db.update(TABLE_CART, values, "name = ?", new String[]{productName});
        db.close();
    }


    public void deleteFromCart(String productName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, "name = ?", new String[]{productName});
        db.close();
    }


    public String getQuantity(String productName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String quantity = "0";

        Cursor cursor = db.rawQuery("SELECT quantity FROM " + TABLE_CART + " WHERE name = ?", new String[]{productName});
        if (cursor != null && cursor.moveToFirst()) {
            quantity = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
            cursor.close();
        }
        db.close();
        return quantity;
    }

}
