package com.example.duan1ne;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1ne.Adapter.ProductAdapter;
import com.example.duan1ne.Model.Product;
import com.example.duan1ne.dao.ProductDao;

import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private ProductDao productDao;
TextView tv_back;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        productDao = new ProductDao(this);

        recyclerView = findViewById(R.id.recyclerView);
        tv_back = findViewById(R.id.tv_back);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Product> productList = productDao.getDsProduct();
        if (productList.isEmpty()) {
            Toast.makeText(this, "No products available", Toast.LENGTH_SHORT).show();
        } else {
            productAdapter = new ProductAdapter(this, productList);
            recyclerView.setAdapter(productAdapter);
        }

        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ViewActivity.this,AdminActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    public void updateCart(List<Product> cartProducts) {
        // Phương thức này có thể được gọi từ Adapter để cập nhật danh sách sản phẩm trong giỏ hàng
        // Nếu cần, bạn có thể cập nhật giao diện hoặc xử lý logic khác tại đây
        // Hiện tại, chỉ in ra danh sách sản phẩm trong giỏ hàng để kiểm tra
        for (Product product : cartProducts) {
            Toast.makeText(this, "Product in cart: " + product.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
