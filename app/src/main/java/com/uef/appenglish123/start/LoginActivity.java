package com.uef.appenglish123.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.uef.appenglish123.R;
import com.uef.appenglish123.fragment.HomeActivity;

import es.dmoral.toasty.Toasty;


public class LoginActivity extends AppCompatActivity {

    TextInputEditText txtEmail, txtMatKhau;
    Button btnDangNhap, btnDangKy;
    FirebaseDatabase fd= FirebaseDatabase.getInstance();
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        //Anh xa view
        txtEmail=(TextInputEditText) findViewById(R.id.txtEmailDN);
        txtMatKhau=(TextInputEditText) findViewById(R.id.txtMatKhauDN);
        btnDangNhap=(Button) findViewById(R.id.btnDangNhap);
        btnDangKy=(Button) findViewById(R.id.btnDangKy);
        firebaseAuth=FirebaseAuth.getInstance();

        //DialogLoading

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               XulyDangNhap();
            }
        });

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void XulyDangNhap(){
        String email=txtEmail.getEditableText().toString().trim();
        String pass=txtMatKhau.getEditableText().toString().trim();

        if(email.isEmpty()){
            txtEmail.setError("Please fill in this field!");
            txtEmail.requestFocus();
            return;
        }else
        if(pass.isEmpty()){
            txtMatKhau.setError("Please fill in this field!");
            txtMatKhau.requestFocus();
            return;
        }else{
            firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toasty.success(LoginActivity.this, "You have successfully logged into your account!", Toast.LENGTH_SHORT, true).show();
                        Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);

                    }else{
                        Toasty.error(LoginActivity.this, "Wrong login information!!", Toasty.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


}