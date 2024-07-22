package com.example.duan1ne.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1ne.Data.Database;
import com.example.duan1ne.Model.Product;

import java.util.ArrayList;

public class ProductDao {
    private static Database database;
    public ProductDao(Context context){
        database = new Database(context);
    }


    public static ArrayList<Product> getDsProduct() {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id, name, price, inCart FROM PRODUCT", null);
        if (cursor != null) {
            int columnIndexInCart = cursor.getColumnIndex("inCart"); // Lấy index của cột "inCart"
            if (columnIndexInCart != -1 && cursor.moveToFirst()) {
                do {
                    @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
                    @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                    @SuppressLint("Range") int price = cursor.getInt(cursor.getColumnIndex("price"));
                    boolean inCart = cursor.getInt(columnIndexInCart) == 1;
                    list.add(new Product(id, name, price, inCart));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    public boolean isProductInCart(int productId) {
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Cart WHERE product_id = ?",
                new String[]{String.valueOf(productId)});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

}


