package com.uef.appenglish123.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.NhomTuVungHolder;
import com.uef.appenglish123.model.NhomTuVung;
import com.uef.appenglish123.vocabulary.LessonActivity;

public class VocabularyFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter<NhomTuVung, NhomTuVungHolder> adapter;
    private ImageView imgV;

    public VocabularyFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_vocabulary, container, false);
        recyclerView = view.findViewById(R.id.recyclerNhomTuVung);

        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        LoadNhomTuVung();


        imgV=view.findViewById(R.id.imgHN);
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Account").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(snapshot.child("hinhnen").getValue().toString()).into(imgV);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }

    private void LoadNhomTuVung() {
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

        adapter = new FirebaseRecyclerAdapter<NhomTuVung, NhomTuVungHolder>(options) {
            @Override
            public NhomTuVungHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_group, parent, false);

                return new NhomTuVungHolder(view);
            }

            @Override
            protected void onBindViewHolder(NhomTuVungHolder holder, final int position, NhomTuVung model) {
                holder.setTxtMaNhom(model.getID());
                holder.setTxtTenNhom(model.getTenNhomTuVung());

                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), LessonActivity.class);
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