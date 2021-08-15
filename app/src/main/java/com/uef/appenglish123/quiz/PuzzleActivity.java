package com.uef.appenglish123.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.DapAnAdapter;
import com.uef.appenglish123.holder.PuzzleHolder;
import com.uef.appenglish123.model.DsTuVung;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import es.dmoral.toasty.Toasty;

public class PuzzleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int index=0;
    ArrayList<String> listTraLoi, listDapAn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_puzzle);

        recyclerView=findViewById(R.id.recyclerPuzzle);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ListTuVung");
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        Load();
    }

    private void Load() {
        Intent intent=getIntent();
        String maBai=intent.getStringExtra("MaBai");
        Toast.makeText(this, ""+maBai, Toast.LENGTH_SHORT).show();
        Query query = databaseReference.orderByChild("MaBai").equalTo(maBai);
        FirebaseRecyclerOptions<DsTuVung> options = new FirebaseRecyclerOptions.Builder<DsTuVung>()
                .setQuery(query, new SnapshotParser<DsTuVung>() {
                    @NonNull
                    @Override
                    public DsTuVung parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new DsTuVung(
                                snapshot.child("TuVung").getValue().toString(),
                                snapshot.child("Nghia").getValue().toString(),
                                snapshot.child("Hinh").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<DsTuVung, PuzzleHolder>(options) {
            @Override
            public PuzzleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_puzzle, parent, false);
                return new PuzzleHolder(view);
            }


            @Override
            protected void onBindViewHolder(PuzzleHolder holder, final int position, DsTuVung model) {
                holder.setTxtTuVung(model.getTuVung());
                holder.setTxtNghia(model.getNghia());
                Picasso.get().load(model.getHinh()).into(holder.imgHinhAnh);

                listTraLoi=new ArrayList<>();
                listDapAn=new ArrayList<>();
                String str=holder.txtTuVung.getText().toString();
                holder.btnNext.setVisibility(View.INVISIBLE);

                holder.btnSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(position == adapter.getItemCount() - 1){
                            Toasty.warning(PuzzleActivity.this,"Đã hết câu đố").show();
                            finish();
                        }
                        else {
                            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                            params.height = 0;
                            params.width = 0;
                            holder.itemView.setLayoutParams(params);
                        }
                    }
                });

                holder.btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(position == adapter.getItemCount() - 1){
                            finish();
                        }
                        else
                        {
                            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                            params.height = 0;
                            params.width = 0;
                            holder.itemView.setLayoutParams(params);
                        }
                    }
                });

                holder.gridDapAn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String s=(String) parent.getItemAtPosition(position);
                        if(s.length()!=0 && index<listTraLoi.size()){
                            for(int i=0; i<listTraLoi.size();i++){
                                if(listTraLoi.get(i).length()==0){
                                    index=i;
                                    break;
                                }
                            }
                            listDapAn.set(position,"");
                            listTraLoi.set(index, s);
                            index++;
                            holder.gridTraLoi.setNumColumns(listTraLoi.size());
                            holder.gridTraLoi.setAdapter(new DapAnAdapter(PuzzleActivity.this, 0, listTraLoi));
                            holder.gridDapAn.setNumColumns(listDapAn.size()/2);
                            holder.gridDapAn.setAdapter(new DapAnAdapter(PuzzleActivity.this, 0, listDapAn));
                            String num="";
                            for(String s1:listTraLoi){
                                num=num+s1;
                            }
                            num=num.toUpperCase();
                            if(num.equals(str.toUpperCase())){
                                Toasty.success(PuzzleActivity.this, "You answered correctly!", Toasty.LENGTH_SHORT).show();
                                holder.btnNext.setVisibility(View.VISIBLE);
                                holder.btnSkip.setVisibility(View.GONE);
                            }
                        }

                    }
                });

                holder.gridTraLoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String s=(String) parent.getItemAtPosition(position);
                        if(s.length()!=0){
                            index=position;
                            listTraLoi.set(position,"");
                            for(int i=0; i<listDapAn.size();i++){
                                if(listDapAn.get(i).length()==0){
                                    listDapAn.set(i,s);
                                    break;
                                }
                            }
                            holder.gridTraLoi.setNumColumns(listTraLoi.size());
                            holder.gridTraLoi.setAdapter(new DapAnAdapter(PuzzleActivity.this, 0, listTraLoi));
                            holder.gridDapAn.setNumColumns(listDapAn.size()/2);
                            holder.gridDapAn.setAdapter(new DapAnAdapter(PuzzleActivity.this, 0, listDapAn));

                        }
                    }
                });
                //XuLy
                index=0;
                listTraLoi.clear();
                listDapAn.clear();
                Random r=new Random();
                for(int i=0; i<str.length();i++){
                    listTraLoi.add("");
                    String s=""+(char)(r.nextInt(26)+65);
                    listDapAn.add(s);
                    String sl=""+(char)(r.nextInt(26)+65);
                    listDapAn.add(sl);
                }

                for(int i=0;i<str.length();i++){
                    String s=""+str.charAt(i);
                    listDapAn.set(i, s.toUpperCase());
                }

                for(int i=0;i< listDapAn.size();i++){
                    String s=listDapAn.get(i);
                    int vt=r.nextInt(listDapAn.size());
                    listDapAn.set(i, listDapAn.get(vt));
                    listDapAn.set(vt, s);

                }
                //SetGridView
                holder.gridTraLoi.setNumColumns(listTraLoi.size());
                holder.gridTraLoi.setAdapter(new DapAnAdapter(PuzzleActivity.this, 0, listTraLoi));
                holder.gridDapAn.setNumColumns(listDapAn.size()/2);
                holder.gridDapAn.setAdapter(new DapAnAdapter(PuzzleActivity.this, 0, listDapAn));

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}