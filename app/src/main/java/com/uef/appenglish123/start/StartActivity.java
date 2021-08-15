package com.uef.appenglish123.start;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.uef.appenglish123.R;
import com.uef.appenglish123.fragment.HomeActivity;


public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_start);

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        new Handler().postDelayed(() ->{
           if(firebaseUser!=null){
               Intent intent=new Intent(StartActivity.this, HomeActivity.class);
             startActivity(intent);
           }else {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }
            finish();
        }, 5000);
    }
}