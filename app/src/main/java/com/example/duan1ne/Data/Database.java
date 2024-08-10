package com.example.duan1ne.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
        String category = "CREATE TABLE CATEGORY(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);";
        dp.execSQL(category);

        //tạo bảng cart
        String cartTable = "CREATE TABLE CART(id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER REFERENCES PRODUCT(id) , name text, price INTEGER, quantity INTEGER, user_id TEXT REFERENCES USER(id))";
        dp.execSQL(cartTable);

        //tạo bảng product
        String product = "CREATE TABLE PRODUCT(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, image BLOB, price INTEGER, inCart INTEGER DEFAULT 0, category_id INTEGER REFERENCES CATEGORY(id))";
        dp.execSQL(product);

        //tạo bảng users
        String users = "CREATE TABLE USER(id TEXT PRIMARY KEY, name TEXT, role INTEGER, email TEXT, phone TEXT, address TEXT)";
        dp.execSQL(users);

        //tạo bảng order
        String order = "CREATE TABLE ORDERS(id id PRIMARY KEY, user_id TEXT REFERENCES USER(id), product_id INTEGER REFERENCES PRODUCT(id), price INTEGER, date TEXT, time TEXT, state TEXT)";
        dp.execSQL(order);

        //tạo bảng cart
        String orderdetail = "CREATE TABLE ORDERDETAIL(id INTEGER PRIMARY KEY AUTOINCREMENT, product_id INTEGER REFERENCES PRODUCT(id) , order_id INTEGER REFERENCES ORDERS(id), name text, price INTEGER, quantity INTEGER, user_id TEXT REFERENCES USER(id))";
        dp.execSQL(orderdetail);

        //thêm dữ liệu mẫu product
        addSampleProducts(dp);
    }

    private void addSampleProducts(SQLiteDatabase dp) {
        int[] categories = {1, 2, 3, 4, 5, 6};
        for (int categoryId : categories) {
            ContentValues values = new ContentValues();
            values.put("name", "Caffe Mocha");
            values.put("image", getBytesFromImage(R.drawable.anh1));
            values.put("price", 450000);
            values.put("category_id", categoryId);
            dp.insert("PRODUCT", null, values);
        }

        dp.execSQL("INSERT INTO USER VALUES ('0rOgurz8E1TmxVpeAqtKeBs9l7k2', 'Test', 1 ,'test123@gmail.com','09622421','TP. HCM')");
    }

    private byte[] getBytesFromImage(int resourceId) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    @Override
    public void onUpgrade(SQLiteDatabase dp, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            dp.execSQL("DROP TABLE IF EXISTS CATEGORY");
            dp.execSQL("DROP TABLE IF EXISTS PRODUCT");
            dp.execSQL("DROP TABLE IF EXISTS CART");
            onCreate(dp);
        }
    }

    // Thêm sản phẩm
    public long addProduct(String name, byte[] image, int price, int categoryId) {
        SQLiteDatabase dp = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("image", image);
        values.put("price", price);
        values.put("category_id", categoryId);
        return dp.insert("PRODUCT", null, values);
    }

//    // Xóa sản phẩm
//    public int deleteProduct(int productId) {
//        SQLiteDatabase dp = this.getWritableDatabase();
//        return dp.delete("PRODUCT", "id = ?", new String[]{String.valueOf(productId)});
//    }
//
//    // Sửa sản phẩm
//    public int updateProduct(int productId, String name, byte[] image, int price, int categoryId) {
//        SQLiteDatabase dp = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("name", name);
//        values.put("image", image);
//        values.put("price", price);
//        values.put("category_id", categoryId);
//        return dp.update("PRODUCT", values, "id = ?", new String[]{String.valueOf(productId)});
//    }
}
