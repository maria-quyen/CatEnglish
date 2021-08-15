package com.uef.appenglish123.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uef.appenglish123.R;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class AddVocabularyActivity extends AppCompatActivity {

    EditText tvTuVung, tvPhienAm, tvLoaiTu, tvNghia, tvCauVD, tvLinkPhatAm, tvLinkHinh;
    Button btnDanhTu, btnTinhTu, btnDongTu, btnTrangTu, btnCreate, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_vocabulary);

        tvTuVung=findViewById(R.id.tvTenTV);
        tvPhienAm=findViewById(R.id.tvPhienAm);
        tvLoaiTu=findViewById(R.id.tvLoaiTu);
        tvNghia=findViewById(R.id.tvNghia);
        tvCauVD=findViewById(R.id.tvCauVD);
        tvLinkPhatAm=findViewById(R.id.tvLinkPhatAm);
        tvLinkHinh=findViewById(R.id.tvLinkAnh);
        btnDanhTu=findViewById(R.id.btnDanhTu);
        btnTinhTu=findViewById(R.id.btnTinhTu);
        btnDongTu=findViewById(R.id.btnDongTu);
        btnTrangTu=findViewById(R.id.btnTrangTu);
        btnCreate=findViewById(R.id.btnAddTuVung);
        btnCancel=findViewById(R.id.btnCancelTuVung);
        Intent intent=getIntent();
        String maBai=intent.getStringExtra("MaBai");
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("ListTuVung").push();
                Map<String,Object> map = new HashMap<>();
                map.put("MaTuVung",databaseReference.getKey());
                map.put("MaBai",maBai);
                map.put("TuVung",tvTuVung.getText().toString());
                map.put("PhienAm",tvPhienAm.getText().toString());
                map.put("LoaiTu",tvLoaiTu.getText().toString());
                map.put("Nghia",tvNghia.getText().toString());
                map.put("ViDu",tvCauVD.getText().toString());
                map.put("PhatAm",tvLinkPhatAm.getText().toString());
                map.put("Hinh",tvLinkHinh.getText().toString());
                databaseReference.setValue(map);
                Toasty.success(AddVocabularyActivity.this, "Thêm từ "+tvTuVung.getText()+" thành công!", Toasty.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AddVocabularyActivity.this, AdminVocabularyActivity.class);
                startActivity(intent);
            }
        });
        XuLyNutLoaiTu();
    }

    private void XuLyNutLoaiTu() {
        btnDanhTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLoaiTu.setText("danh từ");
                btnDanhTu.setBackgroundColor(Color.GREEN);
                btnTinhTu.setBackgroundColor(Color.BLUE);
                btnDongTu.setBackgroundColor(Color.BLUE);
                btnTrangTu.setBackgroundColor(Color.BLUE);
            }
        });

        btnTinhTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLoaiTu.setText("tính từ");
                btnTinhTu.setBackgroundColor(Color.GREEN);
                btnDanhTu.setBackgroundColor(Color.BLUE);
                btnDongTu.setBackgroundColor(Color.BLUE);
                btnTrangTu.setBackgroundColor(Color.BLUE);
            }
        });

        btnDongTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLoaiTu.setText("động từ");
                btnDongTu.setBackgroundColor(Color.GREEN);
                btnDanhTu.setBackgroundColor(Color.BLUE);
                btnTinhTu.setBackgroundColor(Color.BLUE);
                btnTrangTu.setBackgroundColor(Color.BLUE);
            }
        });

        btnTrangTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvLoaiTu.setText("trạng từ");
                btnTrangTu.setBackgroundColor(Color.GREEN);
                btnDanhTu.setBackgroundColor(Color.BLUE);
                btnDongTu.setBackgroundColor(Color.BLUE);
                btnTinhTu.setBackgroundColor(Color.BLUE);
            }
        });
    }
}