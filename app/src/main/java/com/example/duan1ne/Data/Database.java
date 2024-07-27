package com.example.duan1ne.Data;



import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.duan1ne.R;

import java.io.ByteArrayOutputStream;

public class Database extends SQLiteOpenHelper {
    private Context context;
    public Database(Context context) {
        super(context, "magiccoffee", null, 11);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase dp) {
        String category ="CREATE TABLE CATEGORY(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);";
        dp.execSQL(category);

        //tạo bảng cart
        String cartTable = "CREATE TABLE CART(id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER, name text,price interger, quantity INTEGER);";
        dp.execSQL(cartTable);

        //tao bang product
        String product = "CREATE TABLE PRODUCT(id INTEGER PRIMARY KEY AUTOINCREMENT ,name TEXT, image BLOB , price INTEGER, inCart INTEGER DEFAULT 0, category_id INTEGER REFERENCES CATEGORY(id))";
        dp.execSQL(product);
        //them du lieu mau product
        ContentValues values = new ContentValues();
        values.put("name", "Caffe Mocha");
        values.put("image", getBytesFromImage(R.drawable.anh1)); // Sử dụng đối tượng Context đã được lưu trữ
        values.put("price", 450000);
        values.put("category_id", 1);
        dp.insert("PRODUCT", null, values);

        ContentValues values2 = new ContentValues();
        values2.put("name", "Caffe Mocha");
        values2.put("image", getBytesFromImage(R.drawable.anh1));
        values2.put("price", 450000);
        values2.put("category_id", 2);
        dp.insert("PRODUCT", null, values2);

        ContentValues values3 = new ContentValues();
        values3.put("name", "Caffe Mocha");
        values3.put("image", getBytesFromImage(R.drawable.anh1));
        values3.put("price", 450000);
        values3.put("category_id", 3);
        dp.insert("PRODUCT", null, values3);

        ContentValues values4 = new ContentValues();
        values4.put("name", "Caffe Mocha");
        values4.put("image", getBytesFromImage(R.drawable.anh1));
        values4.put("price", 450000);
        values4.put("category_id", 4);
        dp.insert("PRODUCT", null, values4);

        ContentValues values5 = new ContentValues();
        values5.put("name", "Caffe Mocha");
        values5.put("image", getBytesFromImage(R.drawable.anh1));
        values5.put("price", 450000);
        values5.put("category_id", 5);
        dp.insert("PRODUCT", null, values5);

        ContentValues values6 = new ContentValues();
        values6.put("name", "Caffe Mocha");
        values6.put("image", getBytesFromImage(R.drawable.anh1));
        values6.put("price", 450000);
        values6.put("category_id", 6);
        dp.insert("PRODUCT", null, values6);

        ContentValues values7 = new ContentValues();
        values7.put("name", "Caffe Mocha");
        values7.put("image", getBytesFromImage(R.drawable.anh1));
        values7.put("price", 450000);
        values7.put("category_id", 6);
        dp.insert("PRODUCT", null, values7);


        ContentValues values8 = new ContentValues();
        values8.put("name", "Caffe Mocha");
        values8.put("image", getBytesFromImage(R.drawable.anh1));
        values8.put("price", 450000);
        values8.put("category_id", 6);
        dp.insert("PRODUCT", null, values8);




    }
    private byte[] getBytesFromImage(int resourceId) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }



    @Override
    public void onUpgrade(SQLiteDatabase dp, int i, int i1) {
        if(i != i1){
            dp.execSQL("DROP TABLE IF EXISTS CATEGORY");
            dp.execSQL("DROP TABLE IF EXISTS PRODUCT");
            dp.execSQL("DROP TABLE IF EXISTS CART");
            onCreate(dp);
        }
    }
}
