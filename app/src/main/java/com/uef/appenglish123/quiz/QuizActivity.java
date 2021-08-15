package com.uef.appenglish123.quiz;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.R;

import com.uef.appenglish123.holder.QuizHolder;

import com.uef.appenglish123.model.TaiKhoan;
import com.uef.appenglish123.model.TracNghiem;

import java.util.HashMap;

public class QuizActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference databaseReference, ref;
    FirebaseUser firebaseUser;
    int dung=0;
    int sai=0;
    TextView txtTenTK, txtXu, txtDung, txtSai;
    RoundedImageView rivHinhTK;
    LinearLayout linearAddCoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_quiz);
        txtTenTK=findViewById(R.id.tvTenTkScore);
        txtXu=findViewById(R.id.tvCoinHT);
        txtDung=findViewById(R.id.tvCauDung);
        txtSai=findViewById(R.id.tvCauSai);
        rivHinhTK=findViewById(R.id.rivHinhTK);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("Account").child(firebaseUser.getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TaiKhoan tk=snapshot.getValue(TaiKhoan.class);
                txtTenTK.setText(tk.getTen());
                txtXu.setText(tk.getEmail());
                Picasso.get().load(tk.getHinh()).into(rivHinhTK);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = findViewById(R.id.recyclerQuiz);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        databaseReference=FirebaseDatabase.getInstance().getReference("TracNghiem");
        LoadCauHoi();

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void SetFinish(){
        txtDung.setText(String.valueOf(dung));
        txtSai.setText(String.valueOf(sai));
    }

    private void LoadCauHoi() {
        Intent intent=getIntent();
        String maBai=intent.getStringExtra("MaBai");
        Query query = databaseReference.orderByChild("MaBai").equalTo(maBai);
        FirebaseRecyclerOptions<TracNghiem> options = new FirebaseRecyclerOptions.Builder<TracNghiem>()
                .setQuery(query, new SnapshotParser<TracNghiem>() {
                    @NonNull
                    @Override
                    public TracNghiem parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new TracNghiem(
                                snapshot.child("cauHoi").getValue().toString(),
                                snapshot.child("cauA").getValue().toString(),
                                snapshot.child("cauB").getValue().toString(),
                                snapshot.child("cauC").getValue().toString(),
                                snapshot.child("cauD").getValue().toString(),
                                snapshot.child("correct").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<TracNghiem, QuizHolder>(options) {
            @Override
            public QuizHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_quiz, parent, false);

                return new QuizHolder(view);
            }

            @Override
            protected void onBindViewHolder(QuizHolder holder, final int position, TracNghiem model) {
                holder.setTxtCauHoi(model.getCauHoi());
                holder.btnA(model.getCauA());
                holder.btnB(model.getCauB());
                holder.btnC(model.getCauC());
                holder.btnD(model.getCauD());
                holder.setTxtTraLoi(model.getCorrect());
                holder.btnNext.setVisibility(View.INVISIBLE);

                holder.btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(position== adapter.getItemCount()-1){
                            recyclerView.setVisibility(View.GONE);
                            SetFinish();
                        }else {
                            holder.itemView.setVisibility(View.GONE);
                            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                            params.height = 0;
                            params.width = 0;
                            holder.itemView.setLayoutParams(params);
                        }
                    }
                });

                holder.btnA.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(holder.btnA.getText().equals(model.getCorrect())){
                            holder.btnA.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            if(position== adapter.getItemCount()-1) holder.btnNext.setText("SUBMIT");
                            holder.btnNext.setVisibility(View.VISIBLE);
                            holder.btnA.setClickable(false);
                            holder.btnB.setClickable(false);
                            holder.btnC.setClickable(false);
                            holder.btnD.setClickable(false);
                            dung++;
                            return;
                        }else{
                            holder.btnA.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            sai++;
                            if(position== adapter.getItemCount()-1) holder.btnNext.setText("SUBMIT");
                            holder.btnNext.setVisibility(View.VISIBLE);
                            holder.btnA.setClickable(false);
                            holder.btnB.setClickable(false);
                            holder.btnC.setClickable(false);
                            holder.btnD.setClickable(false);
                            if(holder.btnB.getText().equals(model.getCorrect())){
                                holder.btnB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }else if(holder.btnC.getText().equals(model.getCorrect())){
                                holder.btnC.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }else if(holder.btnD.getText().equals(model.getCorrect())){
                                holder.btnD.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }

                        }
                    }
                });

                holder.btnB.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(holder.btnB.getText().equals(model.getCorrect())){
                            holder.btnB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            if(position== adapter.getItemCount()-1) holder.btnNext.setText("SUBMIT");
                            holder.btnNext.setVisibility(View.VISIBLE);
                            holder.btnA.setClickable(false);
                            holder.btnB.setClickable(false);
                            holder.btnC.setClickable(false);
                            holder.btnD.setClickable(false);
                            dung++;
                            return;
                        }else{
                            holder.btnB.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            if(position== adapter.getItemCount()-1) holder.btnNext.setText("SUBMIT");
                            holder.btnNext.setVisibility(View.VISIBLE);
                            holder.btnA.setClickable(false);
                            holder.btnB.setClickable(false);
                            holder.btnC.setClickable(false);
                            holder.btnD.setClickable(false);
                            sai++;
                            if(holder.btnA.getText().equals(model.getCorrect())){
                                holder.btnA.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }else if(holder.btnC.getText().equals(model.getCorrect())){
                                holder.btnC.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }else if(holder.btnD.getText().equals(model.getCorrect())){
                                holder.btnD.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }

                        }
                    }
                });

                holder.btnC.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(holder.btnC.getText().equals(model.getCorrect())){
                            holder.btnC.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            if(position== adapter.getItemCount()-1) holder.btnNext.setText("SUBMIT");
                            holder.btnNext.setVisibility(View.VISIBLE);
                            holder.btnA.setClickable(false);
                            holder.btnB.setClickable(false);
                            holder.btnC.setClickable(false);
                            holder.btnD.setClickable(false);
                            dung++;
                            return;
                        }else{
                            holder.btnC.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            if(position== adapter.getItemCount()-1) holder.btnNext.setText("SUBMIT");
                            holder.btnNext.setVisibility(View.VISIBLE);
                            holder.btnA.setClickable(false);
                            holder.btnB.setClickable(false);
                            holder.btnC.setClickable(false);
                            holder.btnD.setClickable(false);
                            sai++;
                            if(holder.btnA.getText().equals(model.getCorrect())){
                                holder.btnA.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }else if(holder.btnB.getText().equals(model.getCorrect())){
                                holder.btnB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }else if(holder.btnD.getText().equals(model.getCorrect())){
                                holder.btnD.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }

                        }
                    }
                });

                holder.btnD.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        if(holder.btnD.getText().equals(model.getCorrect())){
                            holder.btnD.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                            if(position== adapter.getItemCount()-1) holder.btnNext.setText("SUBMIT");
                            holder.btnNext.setVisibility(View.VISIBLE);
                            holder.btnA.setClickable(false);
                            holder.btnB.setClickable(false);
                            holder.btnC.setClickable(false);
                            holder.btnD.setClickable(false);
                            dung++;
                            return;
                        }else{
                            holder.btnD.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
                            if(position== adapter.getItemCount()-1) holder.btnNext.setText("SUBMIT");
                            holder.btnNext.setVisibility(View.VISIBLE);
                            holder.btnA.setClickable(false);
                            holder.btnB.setClickable(false);
                            holder.btnC.setClickable(false);
                            holder.btnD.setClickable(false);
                            sai++;
                            if(holder.btnA.getText().equals(model.getCorrect())){
                                holder.btnA.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }else if(holder.btnB.getText().equals(model.getCorrect())){
                                holder.btnB.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }else if(holder.btnC.getText().equals(model.getCorrect())){
                                holder.btnC.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                                return;
                            }

                        }
                    }
                });


            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



}