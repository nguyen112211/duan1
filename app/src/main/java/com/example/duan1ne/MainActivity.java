package com.example.duan1ne;

import static com.example.duan1ne.dao.ProductDao.getDsProduct;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1ne.Fagment.CartFragment;
import com.example.duan1ne.Fagment.Homefragment;
import com.example.duan1ne.Fagment.UserFragment;


import com.example.duan1ne.Model.Product;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Homefragment homeFragment = new Homefragment();
    CartFragment cartFragment = new CartFragment();
    UserFragment userFragment = new UserFragment();
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_natigation);
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                if (item.getItemId()== R.id.menu_home) {

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                    return true;
                }
                else if (item.getItemId()== R.id.menu_oder){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,cartFragment).commit();
                    return true;
                }
                else if  (item.getItemId()== R.id.menu_account){
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,userFragment).commit();
                    return true;


                }
                if (selectedFragment != null) {
                    fragmentManager.beginTransaction().replace(R.id.container, selectedFragment).commit();
                    return true;
                }
                return false;
            }
        });

    }
    public void updateCart(ArrayList<Product> productList) {
        // Gọi phương thức cập nhật danh sách sản phẩm trong CartFragment
        if (cartFragment != null) {
            cartFragment.updateProductList(productList);
        }
    }
}
