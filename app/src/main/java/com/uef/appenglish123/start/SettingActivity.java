package com.uef.appenglish123.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
import com.uef.appenglish123.holder.BaiHocHolder;
import com.uef.appenglish123.holder.ImageHolder;
import com.uef.appenglish123.model.BaiHoc;
import com.uef.appenglish123.model.Image;
import com.uef.appenglish123.quiz.FlashcardActivity;
import com.uef.appenglish123.quiz.PuzzleActivity;
import com.uef.appenglish123.quiz.QuizActivity;
import com.uef.appenglish123.quiz.StartTestActivity;
import com.uef.appenglish123.vocabulary.LessonActivity;
import com.uef.appenglish123.vocabulary.VocabularyActivity;

import java.util.HashMap;

public class SettingActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<Image, ImageHolder> adapter;
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        recyclerView = findViewById(R.id.recyclerSetting);
        imgBack=findViewById(R.id.btnReHome);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("HinhNen");

        linearLayoutManager = new LinearLayoutManager(this);
        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        loadHinhNen();
    }

    private void loadHinhNen() {
        Query query = databaseReference;
        FirebaseRecyclerOptions<Image> options = new FirebaseRecyclerOptions.Builder<Image>()
                .setQuery(query, new SnapshotParser<Image>() {
                    @NonNull
                    @Override
                    public Image parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new Image(
                                snapshot.child("img").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<Image, ImageHolder>(options) {
            @Override
            public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_image, parent, false);
                return new ImageHolder(view);
            }


            @Override
            protected void onBindViewHolder(ImageHolder holder, final int position, Image model) {
                Picasso.get().load(model.getImg()).into(holder.img);

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence option[]=new CharSequence[]{"Đặt làm hình nền"};
                        final AlertDialog.Builder builder=new AlertDialog.Builder(SettingActivity.this);
                        builder.setItems(option, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if(i==0){
                                    FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                                    DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Account")
                                            .child(firebaseUser.getUid());

                                    HashMap<String, Object> map=new HashMap<>();
                                    map.put("hinhnen",model.getImg());
                                    ref.updateChildren(map);
                                    finish();
                                }
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