package com.uef.appenglish123.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.BaiHocHolder;
import com.uef.appenglish123.holder.QuizHolder;
import com.uef.appenglish123.holder.TestHolder;
import com.uef.appenglish123.model.BaiHoc;
import com.uef.appenglish123.model.TracNghiem;

import java.util.HashMap;

public class TestActivity extends AppCompatActivity {

    private long TIME= 600000;
    boolean TimerRunning;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<TracNghiem, TestHolder> adapter;
    Button btnSubmit, btnTimer;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);

        btnSubmit=(Button) findViewById(R.id.btnSubmit);
        btnTimer=(Button) findViewById(R.id.btnTimer);
        recyclerView = findViewById(R.id.recyclerListTest);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("TracNghiem");

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        LoadDSCauHoi();
        UpdateTimer();
        recyclerView.setVisibility(View.INVISIBLE);
        btnSubmit.setText("START TESTING");

    }

    private void StartTimer() {
        timer=new CountDownTimer(TIME, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                 TIME=millisUntilFinished;
                 UpdateTimer();

            }

            @Override
            public void onFinish() {
                btnSubmit.setBackgroundColor(Color.GRAY);
                btnSubmit.setClickable(false);
            }
        }.start();
        TimerRunning=true;
        btnSubmit.setText("SUBMIT");
        recyclerView.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void StopTimer(){
        timer.cancel();
        TimerRunning=false;
        btnSubmit.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
        btnSubmit.setClickable(false);
        btnSubmit.setText("SUBMITTED");
        recyclerView.setClickable(false);
    }

    private void UpdateTimer(){
        int minute=(int) TIME/60000;
        int second =(int) TIME%60000/1000;
        String timeText;
        timeText=""+minute;
        timeText+=":";
        if(second<10) timeText+="0";
        timeText+=second;
        btnTimer.setText(timeText);
    }

    private void LoadDSCauHoi() {
        Intent intent=getIntent();
        Query query = databaseReference;
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

        adapter = new FirebaseRecyclerAdapter<TracNghiem, TestHolder>(options) {
            @Override
            public TestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_test, parent, false);

                return new TestHolder(view);
            }

            @Override
            protected void onBindViewHolder(TestHolder holder, final int position, TracNghiem model) {
                holder.setTxtCauHoi(model.getCauHoi());
                holder.chbA(model.getCauA());
                holder.chbB(model.getCauB());
                holder.chbC(model.getCauC());
                holder.chbD(model.getCauD());
                holder.setTxtTraLoi(model.getCorrect());

                //Ẩn các check lại
                holder.checkA.setVisibility(View.INVISIBLE);
                holder.checkB.setVisibility(View.INVISIBLE);
                holder.checkC.setVisibility(View.INVISIBLE);
                holder.checkD.setVisibility(View.INVISIBLE);

                //Kiểm tra nếu câu đó chưa làm
                /*
                if(!holder.chbA.isChecked()&&!holder.chbB.isChecked()&&
                        !holder.chbC.isChecked()&&!holder.chbD.isChecked()){
                    if(holder.chbA.getText().toString().equals(model.getCorrect())){
                        holder.checkA.setBackgroundColor(Color.BLUE);
                        holder.checkB.setBackgroundColor(Color.RED);
                        holder.checkC.setBackgroundColor(Color.RED);
                        holder.checkD.setBackgroundColor(Color.RED);
                    }else if(holder.chbB.getText().toString().equals(model.getCorrect())){
                        holder.checkB.setBackgroundColor(Color.BLUE);
                        holder.checkA.setBackgroundColor(Color.RED);
                        holder.checkC.setBackgroundColor(Color.RED);
                        holder.checkD.setBackgroundColor(Color.RED);
                    }else if(holder.chbC.getText().toString().equals(model.getCorrect())){
                        holder.checkC.setBackgroundColor(Color.BLUE);
                        holder.checkA.setBackgroundColor(Color.RED);
                        holder.checkB.setBackgroundColor(Color.RED);
                        holder.checkD.setBackgroundColor(Color.RED);
                    }else if(holder.chbD.getText().toString().equals(model.getCorrect())){
                        holder.checkD.setBackgroundColor(Color.BLUE);
                        holder.checkA.setBackgroundColor(Color.RED);
                        holder.checkB.setBackgroundColor(Color.RED);
                        holder.checkC.setBackgroundColor(Color.RED);
                    }
                }
                */

                //Xử lý khi nhấn chọn 1 trong các câu trả lời
                holder.chbA.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        holder.chbA.setChecked(true);
                        holder.chbB.setChecked(false);
                        holder.chbC.setChecked(false);
                        holder.chbD.setChecked(false);
                        holder.linearCheck.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                        if(holder.chbA.getText().toString().equals(model.getCorrect())){
                            holder.checkA.setBackgroundColor(Color.BLUE);
                            holder.checkB.setBackgroundColor(Color.WHITE);
                            holder.checkC.setBackgroundColor(Color.WHITE);
                            holder.checkD.setBackgroundColor(Color.WHITE);
                            return;
                        }else{
                            holder.checkA.setBackgroundColor(Color.RED);
                            if(holder.chbB.getText().toString().equals(model.getCorrect())){
                                holder.checkB.setBackgroundColor(Color.BLUE);
                                holder.checkC.setBackgroundColor(Color.WHITE);
                                holder.checkD.setBackgroundColor(Color.WHITE);
                            }else if(holder.chbC.getText().toString().equals(model.getCorrect())){
                                holder.checkC.setBackgroundColor(Color.BLUE);
                                holder.checkB.setBackgroundColor(Color.WHITE);
                                holder.checkD.setBackgroundColor(Color.WHITE);
                            }else if(holder.chbD.getText().toString().equals(model.getCorrect())){
                                holder.checkD.setBackgroundColor(Color.BLUE);
                                holder.checkB.setBackgroundColor(Color.WHITE);
                                holder.checkC.setBackgroundColor(Color.WHITE);
                            }
                        }
                    }
                });

                holder.chbB.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        holder.chbB.setChecked(true);
                        holder.chbA.setChecked(false);
                        holder.chbC.setChecked(false);
                        holder.chbD.setChecked(false);
                        holder.linearCheck.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                        if(holder.chbB.getText().toString().equals(model.getCorrect())){
                            holder.checkB.setBackgroundColor(Color.BLUE);
                            holder.checkA.setBackgroundColor(Color.WHITE);
                            holder.checkC.setBackgroundColor(Color.WHITE);
                            holder.checkD.setBackgroundColor(Color.WHITE);
                            return;
                        }else{
                            holder.checkB.setBackgroundColor(Color.RED);
                            if(holder.chbA.getText().toString().equals(model.getCorrect())){
                                holder.checkA.setBackgroundColor(Color.BLUE);
                                holder.checkC.setBackgroundColor(Color.WHITE);
                                holder.checkD.setBackgroundColor(Color.WHITE);
                            }else if(holder.chbC.getText().toString().equals(model.getCorrect())){
                                holder.checkC.setBackgroundColor(Color.BLUE);
                                holder.checkA.setBackgroundColor(Color.WHITE);
                                holder.checkD.setBackgroundColor(Color.WHITE);
                            }else if(holder.chbD.getText().toString().equals(model.getCorrect())){
                                holder.checkD.setBackgroundColor(Color.BLUE);
                                holder.checkA.setBackgroundColor(Color.WHITE);
                                holder.checkC.setBackgroundColor(Color.WHITE);
                            }
                        }
                    }
                });

                holder.chbC.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        holder.chbC.setChecked(true);
                        holder.chbB.setChecked(false);
                        holder.chbA.setChecked(false);
                        holder.chbD.setChecked(false);
                        holder.linearCheck.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                        if(holder.chbC.getText().toString().equals(model.getCorrect())){
                            holder.checkC.setBackgroundColor(Color.BLUE);
                            holder.checkB.setBackgroundColor(Color.WHITE);
                            holder.checkA.setBackgroundColor(Color.WHITE);
                            holder.checkD.setBackgroundColor(Color.WHITE);
                            return;
                        }else{
                            holder.checkC.setBackgroundColor(Color.RED);
                            if(holder.chbB.getText().toString().equals(model.getCorrect())){
                                holder.checkB.setBackgroundColor(Color.BLUE);
                                holder.checkA.setBackgroundColor(Color.WHITE);
                                holder.checkD.setBackgroundColor(Color.WHITE);
                            }else if(holder.chbA.getText().toString().equals(model.getCorrect())){
                                holder.checkA.setBackgroundColor(Color.BLUE);
                                holder.checkB.setBackgroundColor(Color.WHITE);
                                holder.checkD.setBackgroundColor(Color.WHITE);
                            }else if(holder.chbD.getText().toString().equals(model.getCorrect())){
                                holder.checkD.setBackgroundColor(Color.BLUE);
                                holder.checkB.setBackgroundColor(Color.WHITE);
                                holder.checkA.setBackgroundColor(Color.WHITE);
                            }
                        }
                    }
                });

                holder.chbD.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        holder.chbD.setChecked(true);
                        holder.chbB.setChecked(false);
                        holder.chbC.setChecked(false);
                        holder.chbA.setChecked(false);
                        holder.linearCheck.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW));
                        if(holder.chbD.getText().toString().equals(model.getCorrect())){
                            holder.checkD.setBackgroundColor(Color.BLUE);
                            holder.checkB.setBackgroundColor(Color.WHITE);
                            holder.checkC.setBackgroundColor(Color.WHITE);
                            holder.checkA.setBackgroundColor(Color.WHITE);
                            return;
                        }else{
                            holder.checkD.setBackgroundColor(Color.RED);
                            if(holder.chbB.getText().toString().equals(model.getCorrect())){
                                holder.checkB.setBackgroundColor(Color.BLUE);
                                holder.checkC.setBackgroundColor(Color.WHITE);
                                holder.checkA.setBackgroundColor(Color.WHITE);
                            }else if(holder.chbC.getText().toString().equals(model.getCorrect())){
                                holder.checkC.setBackgroundColor(Color.BLUE);
                                holder.checkB.setBackgroundColor(Color.WHITE);
                                holder.checkA.setBackgroundColor(Color.WHITE);
                            }else if(holder.chbA.getText().toString().equals(model.getCorrect())){
                                holder.checkA.setBackgroundColor(Color.BLUE);
                                holder.checkB.setBackgroundColor(Color.WHITE);
                                holder.checkC.setBackgroundColor(Color.WHITE);
                            }
                        }
                    }
                });

                btnSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(btnTimer.getContext());
                        if(TimerRunning){
                            builder.setTitle("SUBMIT?");
                            builder.setMessage("Bạn có muốn nộp bài ngay bây giờ không?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    StopTimer();
                                    holder.checkA.setVisibility(View.VISIBLE);
                                    holder.checkB.setVisibility(View.VISIBLE);
                                    holder.checkC.setVisibility(View.VISIBLE);
                                    holder.checkD.setVisibility(View.VISIBLE);
                                }
                            });
                        }else{
                            builder.setTitle("THÔNG BÁO");
                            builder.setMessage("Bạn đã sẵn sàng làm bài ngay bây giờ không?");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    StartTimer();
                                }
                            });
                        }
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.show();
                    }
                });
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}