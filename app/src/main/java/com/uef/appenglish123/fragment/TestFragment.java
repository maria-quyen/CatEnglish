package com.uef.appenglish123.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.uef.appenglish123.R;
import com.uef.appenglish123.admin.AddQuizActivity;
import com.uef.appenglish123.holder.LuyenThiHolder;
import com.uef.appenglish123.holder.NhomTuVungHolder;
import com.uef.appenglish123.model.LuyenThi;
import com.uef.appenglish123.model.NhomTuVung;
import com.uef.appenglish123.quiz.TestActivity;
import com.uef.appenglish123.vocabulary.LessonActivity;


public class TestFragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter<LuyenThi, LuyenThiHolder> adapter;

    public TestFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_test, container, false);
        recyclerView = view.findViewById(R.id.recyclerTests);
        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        LoadDsBaiLuyenThi();
        return view;
    }

    private void LoadDsBaiLuyenThi() {
        Query query = FirebaseDatabase.getInstance().getReference().child("LuyenThi");
        FirebaseRecyclerOptions<LuyenThi> options = new FirebaseRecyclerOptions.Builder<LuyenThi>()
                .setQuery(query, new SnapshotParser<LuyenThi>() {
                    @NonNull
                    @Override
                    public LuyenThi parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new LuyenThi(
                                snapshot.child("MaBai").getValue().toString(),
                                snapshot.child("TenBai").getValue().toString(),
                                snapshot.child("Coin").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<LuyenThi, LuyenThiHolder>(options) {
            @Override
            public LuyenThiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_tests, parent, false);

                return new LuyenThiHolder(view);
            }

            @Override
            protected void onBindViewHolder(LuyenThiHolder holder, final int position, LuyenThi model) {
                holder.setTxtMaBai(model.getMaBai());
                holder.setTxtTenBai(model.getTenBai());
                holder.setTxtCoin(model.getCoin());

                holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), TestActivity.class);
                        intent.putExtra("MaBai",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });

                holder.txtCoin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AddQuizActivity.class);
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