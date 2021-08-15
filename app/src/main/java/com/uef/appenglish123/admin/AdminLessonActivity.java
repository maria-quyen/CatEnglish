package com.uef.appenglish123.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.BaiHocAdminHolder;
import com.uef.appenglish123.model.BaiHoc;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class AdminLessonActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnCreate, btnCancel;
    EditText txtTenBaiHoc;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_admin_lesson);

        recyclerView=findViewById(R.id.recyclerLessonAdmin);
        btnCreate=findViewById(R.id.btnThemBaiHoc);
        btnCancel=findViewById(R.id.btnHuyThemBaiHoc);
        txtTenBaiHoc=findViewById(R.id.txtThemBaiHocMoi);


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTenBaiHoc.setText("");
            }
        });

        Intent intent=getIntent();
        String maNhom=intent.getStringExtra("MaNhom");
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("BaiHoc").push();
                Map<String,Object> map = new HashMap<>();
                map.put("MaBai",databaseReference.getKey());
                map.put("MaNhom",maNhom);
                map.put("TenBai",txtTenBaiHoc.getText().toString());
                databaseReference.setValue(map);
                txtTenBaiHoc.setText("");
                Toasty.success(AdminLessonActivity.this, "Thêm nhóm "+txtTenBaiHoc.getText()+" thành công!", Toasty.LENGTH_SHORT).show();
            }
        });

        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(AdminLessonActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        load();
    }

    private void load() {
        Intent intent=getIntent();
        String maNhom=intent.getStringExtra("MaNhom");
        Query query = FirebaseDatabase.getInstance().getReference().child("BaiHoc").orderByChild("MaNhom").equalTo(maNhom);
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

        adapter = new FirebaseRecyclerAdapter<BaiHoc, BaiHocAdminHolder>(options) {
            @Override
            public BaiHocAdminHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_admin_lesson, parent, false);

                return new BaiHocAdminHolder(view);
            }


            @Override
            protected void onBindViewHolder(BaiHocAdminHolder holder, final int position, BaiHoc model) {
                holder.setTxtTenBaiHoc(model.getTenBai());

                holder.imgDel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(holder.txtTenBaiHoc.getContext());
                        builder.setTitle("Are you sure?");
                        builder.setMessage("Deleted data can't be undo.");
                        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseDatabase.getInstance().getReference().child("BaiHoc")
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
                        Intent intent = new Intent(AdminLessonActivity.this, AdminVocabularyActivity.class);
                        intent.putExtra("MaBai",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

                holder.imgEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdminLessonActivity.this, AddQuizActivity.class);
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