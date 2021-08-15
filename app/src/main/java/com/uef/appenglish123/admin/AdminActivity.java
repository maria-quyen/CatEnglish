package com.uef.appenglish123.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.NhomTVAdminHolder;
import com.uef.appenglish123.model.NhomTuVung;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class AdminActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayout linearAdd, linearBtnAdd;
    ImageView imgAdd;
    Button btnCreate, btnCancel;
    EditText txtNhomTV;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin);

        recyclerView=findViewById(R.id.recyclerGroupAdmin);
        linearAdd=findViewById(R.id.linearAddGroup);
        linearBtnAdd=findViewById(R.id.linearBtnAdd);
        imgAdd=findViewById(R.id.imgAdd);
        btnCreate=findViewById(R.id.btnThemNhomTV);
        btnCancel=findViewById(R.id.btnHuyThemNhomTV);
        txtNhomTV=findViewById(R.id.txtThemNhomTV);

        linearAdd.setVisibility(View.GONE);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAdd.setVisibility(View.VISIBLE);
                imgAdd.setVisibility(View.INVISIBLE);
                linearBtnAdd.setVisibility(View.INVISIBLE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearAdd.setVisibility(View.GONE);
                imgAdd.setVisibility(View.VISIBLE);
                linearBtnAdd.setVisibility(View.VISIBLE);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("NhomTuVung").push();
                Map<String,Object> map = new HashMap<>();
                map.put("MaNhom",databaseReference.getKey());
                map.put("TenNhom",txtNhomTV.getText().toString());
                databaseReference.setValue(map);
                linearAdd.setVisibility(View.GONE);
                imgAdd.setVisibility(View.VISIBLE);
                linearBtnAdd.setVisibility(View.VISIBLE);
                Toasty.success(AdminActivity.this, "Thêm nhóm "+txtNhomTV.getText()+"thành công!", Toasty.LENGTH_SHORT).show();
            }
        });

        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(AdminActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Load();

    }

    private void Load() {
        Query query = FirebaseDatabase.getInstance().getReference().child("NhomTuVung");
        FirebaseRecyclerOptions<NhomTuVung> options = new FirebaseRecyclerOptions.Builder<NhomTuVung>()
                .setQuery(query, new SnapshotParser<NhomTuVung>() {
                    @NonNull
                    @Override
                    public NhomTuVung parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new NhomTuVung(
                                snapshot.child("MaNhom").getValue().toString(),
                                snapshot.child("TenNhom").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<NhomTuVung, NhomTVAdminHolder>(options) {
            @Override
            public NhomTVAdminHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_admin_group, parent, false);

                return new NhomTVAdminHolder(view);
            }


            @Override
            protected void onBindViewHolder(NhomTVAdminHolder holder, final int position, NhomTuVung model) {
                holder.setTxtMaNhom(model.getID());
                holder.setTxtTenNhom(model.getTenNhomTuVung());

                holder.imgDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.txtTenNhom.getContext());
                        builder.setTitle("Are you sure?");
                        builder.setMessage("Deleted data can't be undo.");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("NhomTuVung")
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

                holder.imgView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminActivity.this, AdminLessonActivity.class);
                        intent.putExtra("MaNhom",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

}