package com.example.duan1ne.Fagment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1ne.Model.User;
import com.example.duan1ne.R;
import com.example.duan1ne.dao.UserDao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass for displaying and potentially editing user profile information.
 */
public class ProfileFragment extends Fragment {

    private UserDao userDao;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText edtName, edtPhone, edtEmail, edtAddress;
    private Button btnSubmit;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        edtName = view.findViewById(R.id.edtName);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtEmail.setEnabled(false);
        edtAddress = view.findViewById(R.id.edtAddress);
        edtPhone = view.findViewById(R.id.edtPhone);
        btnSubmit = view.findViewById(R.id.btnSubmit);

        userDao = new UserDao(getContext());
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser currentUser = mAuth.getCurrentUser();
                String id = currentUser.getUid();
                String name = edtName.getText().toString();
                String phone = edtPhone.getText().toString();
                String address = edtAddress.getText().toString();
                userDao.updateUserProfile(id, name, phone, address);
                goToAnotherFragment();
            }
        });
        getInfo();
        return view;
    }

    void getInfo(){
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            User user = userDao.getUserById(uid);

            if (user != null) {
                // Set user information in EditText fields
                edtName.setText(user.getName()); // Use user.getName() instead of user.getId()
                edtPhone.setText(user.getPhone());
                edtEmail.setText(user.getEmail());
                edtAddress.setText(user.getAddress());
            } else {
                // Handle case where user not found in database
                Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Handle case where user is not logged in
            Toast.makeText(getContext(), "Please login to view profile", Toast.LENGTH_SHORT).show();
        }
    }

    private void goToAnotherFragment() {
        UserFragment userFragment = new UserFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, userFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
