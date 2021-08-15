package com.uef.appenglish123.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.R;
import com.uef.appenglish123.holder.MessageAdapter;
import com.uef.appenglish123.model.Chat;
import com.uef.appenglish123.model.TaiKhoan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class ChatActivity extends AppCompatActivity {

    TextView TenTK;
    RoundedImageView rivHinh;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference, chatRef;
    ImageView imgSend, btnBack, imgV;
    EditText editSend;
    RecyclerView recyclerView;

    MessageAdapter messageAdapter;
    List<Chat> list;
    String user_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_chat);

        TenTK=findViewById(R.id.showTenTK);
        rivHinh=findViewById(R.id.showHinhTK);
        editSend=findViewById(R.id.editSend);
        imgSend=findViewById(R.id.btnSend);
        btnBack=findViewById(R.id.btnBackHome);
        recyclerView=findViewById(R.id.recyclerInbox);
        recyclerView.setHasFixedSize(true);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        user_id=getIntent().getStringExtra("user_id");
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Account").child(user_id);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TaiKhoan tk=snapshot.getValue(TaiKhoan.class);
                //TenTK.setText(tk.getTen());
                //Picasso.get().load(tk.getHinh()).into(rivHinh);
                TenTK.setText(snapshot.child("ten").getValue().toString());
                Picasso.get().load(snapshot.child("hinh").getValue().toString()).into(rivHinh);

                readMessage(firebaseUser.getUid(), user_id, tk.getHinh());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=editSend.getText().toString();
                if(!msg.isEmpty()){
                    sendMessage((firebaseUser.getUid()), user_id, msg);
                }else{
                    Toasty.warning(ChatActivity.this, "Vui lòng nhập tin nhắn").show();
                }
                editSend.setText("");
            }
        });

        imgV=findViewById(R.id.imgHNinbox);
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

    }

    private void sendMessage(String sender, String receive, String message){
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> map=new HashMap<>();
        map.put("sender",sender);
        map.put("receive",receive);
        map.put("message",message);

        reference.child("TinNhan").push().setValue(map);

        final DatabaseReference chatRef=FirebaseDatabase.getInstance().getReference("ChatList")
                .child(firebaseUser.getUid())
                .child(user_id);
        chatRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    chatRef.child("id").setValue(user_id);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void readMessage(String myid, String userid, String img){
        list= new ArrayList<>();
        chatRef=FirebaseDatabase.getInstance().getReference("TinNhan");
        chatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot item: snapshot.getChildren()){
                    Chat chat=item.getValue(Chat.class);
                    if(chat.getReceive().equals(userid)&& chat.getSender().equals(myid)||
                            chat.getReceive().equals(myid)&& chat.getSender().equals(userid)){
                        list.add(chat);
                    }
                }
                messageAdapter=new MessageAdapter(ChatActivity.this, list,img);
                recyclerView.setAdapter(messageAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}