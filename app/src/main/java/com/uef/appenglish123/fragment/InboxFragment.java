package com.uef.appenglish123.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
import com.uef.appenglish123.model.Chat;
import com.uef.appenglish123.start.ChatActivity;
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.TaiKhoanHolder;
import com.uef.appenglish123.model.TaiKhoan;


public class InboxFragment extends Fragment {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter<TaiKhoan, TaiKhoanHolder> adapter;
    private EditText editSearch;
    private String TinMoiNhat;

    public InboxFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_inbox, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerInbox);
        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        editSearch=view.findViewById(R.id.search_tv);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setList();
        return view;
    }
     private void searchData(String s){
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        Query query=FirebaseDatabase.getInstance().getReference("Account").orderByChild("ten")
                 .startAt(s).endAt(s+"\uf8ff");
         FirebaseRecyclerOptions<TaiKhoan> options = new FirebaseRecyclerOptions.Builder<TaiKhoan>()
                 .setQuery(query, new SnapshotParser<TaiKhoan>() {
                     @NonNull
                     @Override
                     public TaiKhoan parseSnapshot(@NonNull DataSnapshot snapshot) {
                         return new TaiKhoan(
                                 snapshot.child("ten").getValue().toString(),
                                 snapshot.child("email").getValue().toString(),
                                 snapshot.child("hinh").getValue().toString(),
                                 snapshot.child("status").getValue().toString());
                     }
                 })
                 .build();

         adapter = new FirebaseRecyclerAdapter<TaiKhoan, TaiKhoanHolder>(options) {
             @Override
             public TaiKhoanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                 View view = LayoutInflater.from(parent.getContext())
                         .inflate(R.layout.item_chat, parent, false);

                 return new TaiKhoanHolder(view);
             }

             @Override
             protected void onBindViewHolder(TaiKhoanHolder holder, final int position, TaiKhoan model) {
                 holder.setTxtTenTK(model.getTen());
                 holder.setTxtEmail(model.getEmail());
                 Picasso.get().load(model.getHinh()).into(holder.rivTK);
                 holder.showOn.setVisibility(View.INVISIBLE);

                 final String user_id=getRef(position).getKey();
                 assert firebaseUser != null;
                 final String id=firebaseUser.getUid();
                 assert user_id != null;

                 setLastMessage(user_id, holder.txtTinMoiNhat);

                 if(user_id.equals(id)){
                     ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                     params.height = 0;
                     params.width = 0;
                     holder.itemView.setLayoutParams(params);
                 }

                 holder.itemView.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         //Toast.makeText(getContext(),""+user_id, Toast.LENGTH_SHORT).show();
                         Intent intent=new Intent(getActivity(), ChatActivity.class);
                         intent.putExtra("user_id",user_id);
                         startActivity(intent);
                     }
                 });


             }
         };
         recyclerView.setAdapter(adapter);
         adapter.startListening();

     }

    private void setList(){
        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference().child("Account");
        FirebaseRecyclerOptions<TaiKhoan> options = new FirebaseRecyclerOptions.Builder<TaiKhoan>()
                .setQuery(query, new SnapshotParser<TaiKhoan>() {
                    @NonNull
                    @Override
                    public TaiKhoan parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new TaiKhoan(
                                snapshot.child("ten").getValue().toString(),
                                snapshot.child("email").getValue().toString(),
                                snapshot.child("hinh").getValue().toString(),
                                snapshot.child("status").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<TaiKhoan, TaiKhoanHolder>(options) {
            @Override
            public TaiKhoanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_chat, parent, false);

                return new TaiKhoanHolder(view);
            }

            @Override
            protected void onBindViewHolder(TaiKhoanHolder holder, final int position, TaiKhoan model) {
                holder.setTxtTenTK(model.getTen());
                holder.setTxtEmail(model.getEmail());
                Picasso.get().load(model.getHinh()).into(holder.rivTK);
                holder.showOn.setVisibility(View.INVISIBLE);

                final String user_id=getRef(position).getKey();
                assert firebaseUser != null;
                final String id=firebaseUser.getUid();
                assert user_id != null;

                setLastMessage(user_id, holder.txtTinMoiNhat);

                if(user_id.equals(id)){
                    ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                    params.height = 0;
                    params.width = 0;
                    holder.itemView.setLayoutParams(params);
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getContext(),""+user_id, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), ChatActivity.class);
                        intent.putExtra("user_id",user_id);
                        startActivity(intent);
                    }
                });


            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    private void setLastMessage(String id, TextView lastMessage){
        TinMoiNhat="default";
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("TinNhan");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item: snapshot.getChildren()){
                    Chat chat=item.getValue(Chat.class);
                    if((chat.getReceive().equals(firebaseUser.getUid())&& chat.getSender().equals(id)||
                            chat.getReceive().equals(id)&& chat.getSender().equals(firebaseUser.getUid()))){
                        TinMoiNhat=chat.getMessage();
                    }
                }

                switch (TinMoiNhat){
                    case "default":
                        lastMessage.setText("");
                        break;
                    default:
                        lastMessage.setText(TinMoiNhat);
                        break;
                }
                TinMoiNhat="default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}