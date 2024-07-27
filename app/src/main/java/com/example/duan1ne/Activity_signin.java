package com.example.duan1ne;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Activity_signin extends AppCompatActivity {
    private EditText edtemail,edtpassword,edtpassword1;
    private Button btnlogin,btnregister;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        mAuth= FirebaseAuth.getInstance();
        edtemail = findViewById(R.id.edtgmail);
        edtpassword = findViewById(R.id.edtpassword);
        edtpassword1 = findViewById(R.id.edtCfpassword);
        btnregister = findViewById(R.id.btnregister);
        btnlogin = findViewById(R.id.btnlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Activity_signin.this , Activity_login.class);
                startActivity(i);
            }
        });
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
            private void register(){
                String email,password,password1;
                email = edtemail.getText().toString();
                password = edtpassword.getText().toString();
                password1 = edtpassword1.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"vui lòng nhập email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password1)){
                    Toast.makeText(getApplicationContext(),"vui lòng nhập xác nhận mật khẩu",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(password1)){
                    Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(Activity_signin.this , Activity_login.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(getApplicationContext(), "tạo tài khoản khong thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}