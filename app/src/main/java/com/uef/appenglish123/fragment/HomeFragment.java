package com.uef.appenglish123.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
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
import com.uef.appenglish123.holder.DictionaryHolder;
import com.uef.appenglish123.model.DsTuVung;
import com.uef.appenglish123.start.SettingActivity;
import com.uef.appenglish123.vocabulary.VocabularyDetailActivity;



public class HomeFragment extends Fragment {
    
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter<DsTuVung, DictionaryHolder> adapter;
    private EditText editSearch;
    private ImageView imgLanguage, imgHinhNen;
    private String str="english";
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private FloatingActionButton fab;
    private ProgressDialog progressDialog;


    public HomeFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        //loading
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        //Ánh xạ view
        imgHinhNen=view.findViewById(R.id.imgHinhNen);
        recyclerView = view.findViewById(R.id.SearchDictionary);
        editSearch=view.findViewById(R.id.editTimKiem);
        imgLanguage=view.findViewById(R.id.btnLanguage);
        imgLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence option[]=new CharSequence[]{"English","Vietnamese"};
                final android.app.AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Chọn ngôn ngữ");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                       if(i==0){
                           Picasso.get().load(R.drawable.ic_english).into(imgLanguage);
                           str="english";
                           editSearch.setText("");
                           recyclerView.setVisibility(View.INVISIBLE);

                       }
                       if(i==1){
                           Picasso.get().load(R.drawable.ic_vietnam).into(imgLanguage);
                           str="vietnamese";
                           editSearch.setText("");
                           recyclerView.setVisibility(View.INVISIBLE);
                       }
                    }
                });
                builder.show();
            }
        });

        recyclerView.setVisibility(View.INVISIBLE);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                progressDialog.show();
                if(editSearch.getText().equals("")){

                }else if(str.equals("english")){
                    LoadTuDienAnh(s.toString());
                    recyclerView.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }else if(str.equals("vietnamese")){
                    LoadTuDienViet(s.toString());
                    recyclerView.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        //Đặt hình nền
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Account").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get().load(snapshot.child("hinhnen").getValue().toString()).into(imgHinhNen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        fab=view.findViewById(R.id.floatImage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });

        return view;
    }


    private void LoadTuDienAnh(String s) {
        Query query = FirebaseDatabase.getInstance().getReference().child("ListTuVung").orderByChild("TuVung").startAt(s).endAt(s+"\uf8ff");
        FirebaseRecyclerOptions<DsTuVung> options = new FirebaseRecyclerOptions.Builder<DsTuVung>()
                .setQuery(query, new SnapshotParser<DsTuVung>() {
                    @NonNull
                    @Override
                    public DsTuVung parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new DsTuVung(
                                snapshot.child("MaTuVung").getValue().toString(),
                                snapshot.child("TuVung").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<DsTuVung, DictionaryHolder>(options) {
            @Override
            public DictionaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_dictionary, parent, false);

                return new DictionaryHolder(view);
            }

            @Override
            protected void onBindViewHolder(DictionaryHolder holder, final int position, DsTuVung model) {
                holder.setTxtTuVung(model.getTuVung());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), VocabularyDetailActivity.class);
                        intent.putExtra("MaTuVung",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void LoadTuDienViet(String s) {
        Query query = FirebaseDatabase.getInstance().getReference().child("ListTuVung").orderByChild("Nghia").startAt(s).endAt(s+"\uf8ff");
        FirebaseRecyclerOptions<DsTuVung> options = new FirebaseRecyclerOptions.Builder<DsTuVung>()
                .setQuery(query, new SnapshotParser<DsTuVung>() {
                    @NonNull
                    @Override
                    public DsTuVung parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new DsTuVung(
                                snapshot.child("MaTuVung").getValue().toString(),
                                snapshot.child("Nghia").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<DsTuVung, DictionaryHolder>(options) {
            @Override
            public DictionaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_dictionary, parent, false);

                return new DictionaryHolder(view);
            }

            @Override
            protected void onBindViewHolder(DictionaryHolder holder, final int position, DsTuVung model) {
                holder.setTxtTuVung(model.getTuVung());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), VocabularyDetailActivity.class);
                        intent.putExtra("MaTuVung",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}

