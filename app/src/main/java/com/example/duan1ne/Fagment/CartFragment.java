package com.example.duan1ne.Fagment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.duan1ne.Activity_login;
import com.example.duan1ne.Adapter.CartAdapter;
import com.example.duan1ne.Adapter.ProductAdapter;
import com.example.duan1ne.MainActivity;
import com.example.duan1ne.Model.Cart;
import com.example.duan1ne.Model.Product;
import com.example.duan1ne.R;
import com.example.duan1ne.dao.CartDao;
import com.example.duan1ne.dao.ProductDao;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {
    private CartDao cartDao;
    private ArrayList<Cart> listCart;
    RecyclerView recyclerCart;
    TextView total;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerCart = view.findViewById(R.id.recyclerViewCart);
        total = view.findViewById(R.id.txtTotalPrice);
        Button btnCheckout = view.findViewById(R.id.buttonCheckout);
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String totalAmount = total.getText().toString();
                BillFragment billFragment = BillFragment.newInstance(totalAmount,null);

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.container, billFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        loadData();
        return view;
    }

    private void loadData(){
        cartDao = new CartDao(getContext());
        listCart = cartDao.getDsCart();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerCart.setLayoutManager(linearLayoutManager);
        CartAdapter adapter = new CartAdapter(getContext(), listCart, total);
        recyclerCart.setAdapter(adapter);
    }

}
