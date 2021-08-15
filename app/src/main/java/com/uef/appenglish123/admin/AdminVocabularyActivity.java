package com.uef.appenglish123.admin;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.TuVungAdminHolder;
import com.uef.appenglish123.model.DsTuVung;

public class AdminVocabularyActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnThem;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_vocabulary);

        recyclerView=findViewById(R.id.recyclerVocabularyAdmin);
        btnThem=findViewById(R.id.btnThemTuVungMoi);

        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(AdminVocabularyActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Intent intent=getIntent();
        String maBai=intent.getStringExtra("MaBai");
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminVocabularyActivity.this, AddVocabularyActivity.class);
                intent.putExtra("MaBai",maBai);
                startActivity(intent);
            }
        });

        load();
    }

    private void load() {
        Intent intent=getIntent();
        String maBai=intent.getStringExtra("MaBai");
        final Query query = FirebaseDatabase.getInstance().getReference().child("ListTuVung").orderByChild("MaBai").equalTo(maBai);
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
                                snapshot.child("Hinh").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<DsTuVung, TuVungAdminHolder>(options) {
            @Override
            public TuVungAdminHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_admin_vocabulary, parent, false);
                return new TuVungAdminHolder(view);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void onBindViewHolder(TuVungAdminHolder holder, final int position, DsTuVung model) {
                holder.setTxtMaTV(model.getID());
                holder.setTxtMaBai(model.getMaBai());
                holder.setTxtTuVung(model.getTuVung());
                holder.setTxtPhienAm(model.getPhienAm());
                holder.setTxtLoai(model.getLoaiTu());
                holder.setTxtNghia(model.getNghia());
                Picasso.get().load(model.getHinh()).into(holder.riv);

                holder.btnDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.txtTuVung.getContext());
                        builder.setTitle("Are you sure?");
                        builder.setMessage("Deleted data can't be undo.");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("ListTuVung")
                                        .child(getRef(position).getKey()).removeValue();
                            }
                        });
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