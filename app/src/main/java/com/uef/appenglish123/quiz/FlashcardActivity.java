package com.uef.appenglish123.quiz;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.FlashcardHolder;
import com.uef.appenglish123.model.DsTuVung;

import java.util.HashMap;
import java.util.Random;

public class FlashcardActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_flashcard);

        recyclerView = findViewById(R.id.recyclerFlashcard);

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

        Intent intent = getIntent();
        String maBai = intent.getStringExtra("MaBai");
        Toast.makeText(this, "" + maBai, Toast.LENGTH_SHORT).show();
        Query query = databaseReference.orderByChild("MaBai").equalTo(maBai);
        FirebaseRecyclerOptions<DsTuVung> options = new FirebaseRecyclerOptions.Builder<DsTuVung>()
                .setQuery(query, new SnapshotParser<DsTuVung>() {
                    @NonNull
                    @Override
                    public DsTuVung parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new DsTuVung(
                                snapshot.child("TuVung").getValue().toString(),
                                snapshot.child("PhienAm").getValue().toString(),
                                snapshot.child("Nghia").getValue().toString(),
                                snapshot.child("PhatAm").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<DsTuVung, FlashcardHolder>(options) {
            @Override
            public FlashcardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_flashcard, parent, false);
                return new FlashcardHolder(view);
            }


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            protected void onBindViewHolder(FlashcardHolder holder, final int position, DsTuVung model) {

                holder.setTxtTuVung(model.getTuVung());
                holder.setTxtPhienAm(model.getPhienAm());
                holder.setTxtNghia(model.getNghia());
                holder.setTxtLinkPhatAm(model.getPhatAm());

                holder.linearAfter.setVisibility(View.INVISIBLE);
                holder.btnNext.setVisibility(View.INVISIBLE);

                holder.linearBefore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.linearBefore.setVisibility(View.GONE);
                        holder.linearAfter.setVisibility(View.VISIBLE);
                        holder.btnNext.setVisibility(View.VISIBLE);
                        if (position == adapter.getItemCount() - 1)
                            holder.btnNext.setText("FINISH");

                    }
                });

                holder.btnNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == adapter.getItemCount() - 1) {
                            finish();
                        } else {
                            holder.itemView.setVisibility(View.GONE);
                            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                            params.height = 0;
                            params.width = 0;
                            holder.itemView.setLayoutParams(params);
                        }
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }



}