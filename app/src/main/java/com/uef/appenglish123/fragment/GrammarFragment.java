package com.uef.appenglish123.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.uef.appenglish123.start.ViewDetailActivity;
import com.uef.appenglish123.holder.NguPhapHolder;
import com.uef.appenglish123.model.NguPhap;
import com.uef.appenglish123.quiz.QuizActivity;


public class GrammarFragment extends Fragment {

    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    FloatingActionButton fabTest;
    ImageView imgV;

    public GrammarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_grammar, container, false);
        recyclerView = view.findViewById(R.id.recyclerGrammar);
        fabTest=view.findViewById(R.id.floatTest);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        LoadGrammar();
        SetFloat();

        imgV=view.findViewById(R.id.imgHNGrammar);
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

    String ex1="ex1";
    String ex2="ex2";
    private void SetFloat() {
        fabTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence option[]=new CharSequence[]{"Bài tập 1","Bài tập 2"};
                final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Chọn một bài");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0){
                            Intent intent=new Intent(getActivity(), QuizActivity.class);
                            intent.putExtra("MaBai",ex1);
                            startActivity(intent);
                        }
                        if(i==1){
                            Intent intent=new Intent(getActivity(), QuizActivity.class);
                            intent.putExtra("MaBai",ex2);
                            startActivity(intent);
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void LoadGrammar() {
        Query query = FirebaseDatabase.getInstance().getReference().child("NguPhap");
        FirebaseRecyclerOptions<NguPhap> options = new FirebaseRecyclerOptions.Builder<NguPhap>()
                .setQuery(query, new SnapshotParser<NguPhap>() {
                    @NonNull
                    @Override
                    public NguPhap parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new NguPhap(
                                snapshot.child("MaNP").getValue().toString(),
                                snapshot.child("TenNP").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<NguPhap, NguPhapHolder>(options) {
            @Override
            public NguPhapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_grammar, parent, false);
                return new NguPhapHolder(view);
            }


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            protected void onBindViewHolder(NguPhapHolder holder, final int position, NguPhap model) {
                holder.txtTenNP.setText(model.getTenNP());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(), ViewDetailActivity.class);
                        intent.putExtra("MaNP",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }

        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}