package com.uef.appenglish123.vocabulary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uef.appenglish123.quiz.PuzzleActivity;
import com.uef.appenglish123.quiz.FlashcardActivity;
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.BaiHocHolder;
import com.uef.appenglish123.model.BaiHoc;
import com.uef.appenglish123.quiz.StartTestActivity;

import java.util.HashMap;


public class LessonActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<BaiHoc, BaiHocHolder> adapter;

    String maNhom="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lesson);

        recyclerView = findViewById(R.id.recyclerLesson);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("BaiHoc");

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        load();
    }

    private void load() {
        if (getIntent() != null) {
            maNhom = getIntent().getStringExtra("MaNhom");
        }
        if (!maNhom.isEmpty() && maNhom != null) {
            Query query = databaseReference.orderByChild("MaNhom").equalTo(maNhom);
            FirebaseRecyclerOptions<BaiHoc> options = new FirebaseRecyclerOptions.Builder<BaiHoc>()
                    .setQuery(query, new SnapshotParser<BaiHoc>() {
                        @NonNull
                        @Override
                        public BaiHoc parseSnapshot(@NonNull DataSnapshot snapshot) {
                            return new BaiHoc(
                                    snapshot.child("MaBai").getValue().toString(),
                                    snapshot.child("TenBai").getValue().toString());
                        }
                    })
                    .build();

            adapter = new FirebaseRecyclerAdapter<BaiHoc, BaiHocHolder>(options) {
                @Override
                public BaiHocHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_lesson, parent, false);
                    return new BaiHocHolder(view);
                }


                @Override
                protected void onBindViewHolder(BaiHocHolder holder, final int position, BaiHoc model) {
                    holder.setTxtMaBai(model.getMaBai());
                    holder.setTxtTenBai(model.getTenBai());

                    final BaiHoc local = model;
                    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(LessonActivity.this, VocabularyActivity.class);
                            intent.putExtra("MaBai",adapter.getRef(position).getKey());
                            startActivity(intent);
                        }
                    });

                    holder.imgFlashCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(LessonActivity.this, FlashcardActivity.class);
                            intent.putExtra("MaBai",adapter.getRef(position).getKey());
                            startActivity(intent);
                        }
                    });

                    holder.imgPuzzle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(LessonActivity.this, PuzzleActivity.class);
                            intent.putExtra("MaBai",adapter.getRef(position).getKey());
                            startActivity(intent);
                        }
                    });

                    holder.imgQuiz.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(LessonActivity.this, StartTestActivity.class);
                            intent.putExtra("MaBai",adapter.getRef(position).getKey());
                            startActivity(intent);
                        }
                    });

                }
            };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }
    }


}

