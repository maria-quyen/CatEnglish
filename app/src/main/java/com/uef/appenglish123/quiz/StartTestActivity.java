package com.uef.appenglish123.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.uef.appenglish123.R;
import com.uef.appenglish123.start.StartActivity;
import com.uef.appenglish123.vocabulary.LessonActivity;


public class StartTestActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_start_quiz);

        new Handler().postDelayed(() ->{
            Intent intent=getIntent();
            String maBai=intent.getStringExtra("MaBai");
            Intent it = new Intent(StartTestActivity.this, QuizActivity.class);
            it.putExtra("MaBai",maBai);
            startActivity(it);
            finish();
        }, 3000);


    }
}