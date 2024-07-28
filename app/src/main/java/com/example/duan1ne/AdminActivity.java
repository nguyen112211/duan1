package com.example.duan1ne;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duan1ne.Data.Database;

import java.io.ByteArrayOutputStream;

public class AdminActivity extends Activity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private EditText etProductName, etProductPrice, etCategoryId;
    private ImageView ivProductImage;
    private Button btnSelectImage, btnAddProduct, btnUpdateProduct, btnDeleteProduct,btnViewProduct;
    private Database db;
    private Bitmap productImage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = new Database(this);

        etProductName = findViewById(R.id.etProductName);
        etProductPrice = findViewById(R.id.etProductPrice);
        etCategoryId = findViewById(R.id.etCategoryId);
        ivProductImage = findViewById(R.id.ivProductImage);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);
        btnDeleteProduct = findViewById(R.id.btnDeleteProduct);
        btnViewProduct = findViewById(R.id.btnViewProduct);

        btnSelectImage.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

        });

        btnViewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AdminActivity.this,ViewActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnAddProduct.setOnClickListener(v -> addProduct());
        btnUpdateProduct.setOnClickListener(v -> updateProduct());
        btnDeleteProduct.setOnClickListener(v -> deleteProduct());
    }

    private void addProduct() {
        String name = etProductName.getText().toString();
        int price = Integer.parseInt(etProductPrice.getText().toString());
        int categoryId = Integer.parseInt(etCategoryId.getText().toString());
        byte[] image = getImageBytes(productImage);

        long result = db.addProduct(name, image, price, categoryId);
        if (result != -1) {
            Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
            resetFields();
        } else {
            Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateProduct() {
        String name = etProductName.getText().toString();
        int price = Integer.parseInt(etProductPrice.getText().toString());
        int categoryId = Integer.parseInt(etCategoryId.getText().toString());
        byte[] image = getImageBytes(productImage);

        int productId = 1; // Thay thế bằng giá trị productId thực tế
        int result = db.updateProduct(productId, name, image, price, categoryId);
        if (result > 0) {
            Toast.makeText(this, "Sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();
            resetFields();
        } else {
            Toast.makeText(this, "Sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteProduct() {
        int productId = 1; // Thay thế bằng giá trị productId thực tế
        int result = db.deleteProduct(productId);
        if (result > 0) {
            Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private byte[] getImageBytes(Bitmap bitmap) {
        if (bitmap == null) return null;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void resetFields() {
        etProductName.setText("");
        etProductPrice.setText("");
        etCategoryId.setText("");
        ivProductImage.setImageResource(android.R.color.transparent);
        productImage = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            productImage = (Bitmap) extras.get("data");
            ivProductImage.setImageBitmap(productImage);
        }
    }
}
