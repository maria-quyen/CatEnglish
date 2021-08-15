package com.uef.appenglish123.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class VocabAdminFragment extends Fragment {

    RecyclerView recyclerView;
    LinearLayout linearAdd, linearBtnAdd;
    ImageView imgAdd;
    Button btnCreate, btnCancel;
    EditText txtNhomTV;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;

    public VocabAdminFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_vocab_admin, container, false);

        recyclerView=view.findViewById(R.id.recyclerGroupAdmin);
        linearAdd=view.findViewById(R.id.linearAddGroup);
        linearBtnAdd=view.findViewById(R.id.linearBtnAdd);
        imgAdd=view.findViewById(R.id.imgAdd);
        btnCreate=view.findViewById(R.id.btnThemNhomTV);
        btnCancel=view.findViewById(R.id.btnHuyThemNhomTV);
        txtNhomTV=view.findViewById(R.id.txtThemNhomTV);

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
                Toasty.success(getActivity(), "Thêm nhóm "+txtNhomTV.getText()+"thành công!", Toasty.LENGTH_SHORT).show();
            }
        });

        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        Load();
        return view;
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

            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}