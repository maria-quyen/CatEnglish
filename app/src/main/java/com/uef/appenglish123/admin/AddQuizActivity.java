package com.uef.appenglish123.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uef.appenglish123.R;
import com.uef.appenglish123.model.TracNghiem;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class AddQuizActivity extends AppCompatActivity {

    EditText txtCauHoi, txtA, txtB, txtC, txtD, txtMaBai;
    TextView txtTraLoi;
    CheckBox chbA, chbB, chbC, chbD;
    Button btnCreate, btnXoaHet;
    DatabaseReference ref;
    String maBai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_quiz);

        txtCauHoi=(EditText) findViewById(R.id.txtTaoCauHoi);
        txtTraLoi=(TextView) findViewById(R.id.txtOptionAnswer);
        txtMaBai=(EditText) findViewById(R.id.tvMaBaiQuiz);
        txtA=(EditText) findViewById(R.id.txtOptionA);
        txtB=(EditText) findViewById(R.id.txtOptionB);
        txtC=(EditText) findViewById(R.id.txtOptionC);
        txtD=(EditText) findViewById(R.id.txtOptionD);
        chbA=(CheckBox) findViewById(R.id.chbOptionA);
        chbB=(CheckBox) findViewById(R.id.chbOptionB);
        chbC=(CheckBox) findViewById(R.id.chbOptionC);
        chbD=(CheckBox) findViewById(R.id.chbOptionD);
        btnCreate=(Button) findViewById(R.id.btnCreateQuestion);
        btnXoaHet=(Button) findViewById(R.id.btnXoaHet);

        btnXoaHet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtCauHoi.setText("");
                txtTraLoi.setText("");
                txtA.setText("");
                txtB.setText("");
                txtC.setText("");
                txtD.setText("");
                chbA.setChecked(false);
                chbB.setChecked(false);
                chbC.setChecked(false);
                chbD.setChecked(false);
                btnCreate.setEnabled(true);
            }
        });

        CheckAnswer();
        ref= FirebaseDatabase.getInstance().getReference().child("TracNghiem");
        Intent intent=getIntent();
        maBai=intent.getStringExtra("MaBai");
        txtMaBai.setText(maBai);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeQuestion();
            }

        });
    }
    private void makeQuestion() {
        String cauhoi=txtCauHoi.getEditableText().toString();
        String cauA=txtA.getText().toString();
        String cauB=txtB.getText().toString();
        String cauC=txtC.getText().toString();
        String cauD=txtD.getText().toString();
        String traLoi=txtTraLoi.getText().toString();

        if(cauhoi.isEmpty()){
            txtCauHoi.setError("Please fill in the answer for this field!");
            txtCauHoi.requestFocus();
            return;
        }else if(cauA.isEmpty()){
            txtA.setError("Please fill in the answer for this field!");
            txtA.requestFocus();
            return;
        }else if(cauB.isEmpty()){
            txtB.setError("Please fill in the answer for this field!");
            txtB.requestFocus();
            return;
        }else if(cauC.isEmpty()){
            txtC.setError("Please fill in the answer for this field!");
            txtC.requestFocus();
            return;
        }else if(cauD.isEmpty()){
            txtD.setError("Please fill in the answer for this field!");
            txtD.requestFocus();
            return;
        }else if(traLoi.isEmpty()) {
            Toasty.error(this, "Please tick the correct answer box!", Toasty.LENGTH_SHORT).show();

        }else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("TracNghiem").push();
            Map<String,Object> map = new HashMap<>();
            map.put("maCH",databaseReference.getKey());
            map.put("MaBai",txtMaBai.getText().toString());
            map.put("cauHoi",txtCauHoi.getText().toString());
            map.put("cauA",txtA.getText().toString());
            map.put("cauB",txtB.getText().toString());
            map.put("cauC",txtC.getText().toString());
            map.put("cauD",txtD.getText().toString());
            map.put("correct",txtTraLoi.getText().toString());
            databaseReference.setValue(map);
            btnCreate.setEnabled(false);
            Toasty.success(AddQuizActivity.this, "Thêm câu hỏi "+txtCauHoi.getText()+" thành công!", Toasty.LENGTH_SHORT).show();
        }
    }

    private void CheckAnswer(){
        chbA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtA.getText().length()==0){
                    txtA.setError("Vui lòng điền đáp án cho dòng này!");
                    chbA.setChecked(false);
                }else{
                    chbA.setChecked(true);
                    txtTraLoi.setText(txtA.getText().toString());
                    chbB.setChecked(false);
                    chbC.setChecked(false);
                    chbD.setChecked(false);
                }
            }
        });

        chbB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtB.getText().length()==0){
                    txtB.setError("Please fill in the answer for this field!");
                    chbB.setChecked(false);
                }else{
                    chbB.setChecked(true);
                    txtTraLoi.setText(txtB.getText().toString());
                    chbA.setChecked(false);
                    chbC.setChecked(false);
                    chbD.setChecked(false);
                }
            }
        });

        chbC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtC.getText().length()==0){
                    txtC.setError("Please fill in the answer for this field!");
                    chbC.setChecked(false);
                }else{
                    chbC.setChecked(true);
                    txtTraLoi.setText(txtC.getText().toString());
                    chbA.setChecked(false);
                    chbB.setChecked(false);
                    chbD.setChecked(false);
                }
            }
        });

        chbD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtD.getText().length()==0){
                    txtD.setError("Please fill in the answer for this field!");
                    chbD.setChecked(false);
                }else{
                    chbD.setChecked(true);
                    txtTraLoi.setText(txtD.getText().toString());
                    chbA.setChecked(false);
                    chbB.setChecked(false);
                    chbC.setChecked(false);
                }
            }
        });

    }
}

