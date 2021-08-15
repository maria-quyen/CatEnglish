package com.uef.appenglish123.vocabulary;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.uef.appenglish123.holder.TuVungHolder;
import com.uef.appenglish123.model.DsTuVung;
import com.uef.appenglish123.quiz.StartTestActivity;

import java.io.IOException;
import java.util.HashMap;

public class VocabularyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter adapter;
    String maBai="";
    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vocabulary);

        recyclerView = findViewById(R.id.recyclerVocabulary);
       recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setHasFixedSize(true);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("ListTuVung");


        if (getIntent() != null) {
            maBai = getIntent().getStringExtra("MaBai");
        }
        if (!maBai.isEmpty() && maBai != null) {
            load(maBai);
        }
    }

    private void load(String maBai) {
        Toast.makeText(this, ""+maBai, Toast.LENGTH_SHORT).show();
        final Query query = databaseReference.orderByChild("MaBai").equalTo(maBai);
            FirebaseRecyclerOptions<DsTuVung> options = new FirebaseRecyclerOptions.Builder<DsTuVung>()
                    .setQuery(query, new SnapshotParser<DsTuVung>() {
                        @NonNull
                        @Override
                        public DsTuVung parseSnapshot(@NonNull DataSnapshot snapshot) {
                            return new DsTuVung(
                                    snapshot.child("MaTuVung").getValue().toString(),
                                    snapshot.child("MaBai").getValue().toString(),
                                    snapshot.child("TuVung").getValue().toString(),
                                    snapshot.child("PhienAm").getValue().toString(),
                                    snapshot.child("LoaiTu").getValue().toString(),
                                    snapshot.child("Nghia").getValue().toString(),
                                    snapshot.child("PhatAm").getValue().toString(),
                                    snapshot.child("PhatAmCham").getValue().toString(),
                                    snapshot.child("ViDu").getValue().toString(),
                                    snapshot.child("Hinh").getValue().toString());
                        }
                    })
                    .build();

            adapter = new FirebaseRecyclerAdapter<DsTuVung, TuVungHolder>(options) {
                @Override
                public TuVungHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_vocabulary, parent, false);
                    return new TuVungHolder(view);
                }

                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                protected void onBindViewHolder(TuVungHolder holder, final int position, DsTuVung model) {
                    String slow=model.getSlow();
                    holder.setTxtMaTV(model.getID());
                    holder.setTxtMaBai(model.getMaBai());
                    holder.setTxtTuVung(model.getTuVung());
                    holder.setTxtPhienAm(model.getPhienAm());
                    if (model.getLoaiTu().equals("danh từ")) {
                        holder.linearLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffff66")));
                    } else if (model.getLoaiTu().equals("tính từ")) {
                        holder.linearLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff99ff")));
                    } else if (model.getLoaiTu().equals("động từ")) {
                        holder.linearLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#99ff00")));
                    } else if (model.getLoaiTu().equals("trạng từ")) {
                        holder.linearLayout.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#66ccff")));
                    }
                    holder.setTxtNghia(model.getNghia());
                    holder.setTxtLinkPhatAm(model.getPhatAm());
                    holder.setTxtViDu(model.getViDu());
                    Picasso.get().load(model.getHinh()).into(holder.imgTuVung);

                    holder.btnSpeaker.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                player = new MediaPlayer();
                                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                player.setDataSource(holder.txtLinkPhatAm.getText().toString());
                                player.prepare();
                                player.start();
                            } catch (IOException ex) {
                                Toast.makeText(VocabularyActivity.this, "Error!" + ex, Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    holder.btnSnail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                player = new MediaPlayer();
                                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                player.setDataSource(slow);
                                player.prepare();
                                player.start();
                            } catch (IOException ex) {
                                Toast.makeText(VocabularyActivity.this, "Error!" + ex, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    final DsTuVung local = model;
                    holder.linearDetail.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(VocabularyActivity.this, VocabularyDetailActivity.class);
                            intent.putExtra("MaTuVung", local.getID());
                            intent.putExtra("TuVung", local.getTuVung());
                            intent.putExtra("PhienAm", local.getPhienAm());
                            intent.putExtra("LoaiTu", local.getLoaiTu());
                            intent.putExtra("Nghia", local.getNghia());
                            intent.putExtra("PhatAm", local.getPhatAm());
                            intent.putExtra("PhatAmCham", local.getSlow());
                            intent.putExtra("ViDu", local.getViDu());
                            intent.putExtra("Hinh", local.getHinh());
                            startActivity(intent);
                        }
                    });



                }

            };
            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }
}