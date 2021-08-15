package com.uef.appenglish123.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.R;

import java.text.DateFormat;
import java.util.Date;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {

    TextView txtTenTK, txtEmail;
    Button btnKetBan;
    ImageView imgHinh;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference, RequestRef, friendRef, notificationRef;
    FirebaseUser firebaseUser;
    String current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);

        String user_id=getIntent().getStringExtra("user_id");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Account").child(user_id);
        RequestRef=FirebaseDatabase.getInstance().getReference().child("YeuCauKB");
        friendRef=FirebaseDatabase.getInstance().getReference().child("BanBe");
        notificationRef=FirebaseDatabase.getInstance().getReference().child("ThongBao");

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        imgHinh=(ImageView) findViewById(R.id.img_HinhTK);
        txtTenTK=(TextView) findViewById(R.id.tv_TenTK);
        txtEmail=(TextView) findViewById(R.id.tv_email);
        btnKetBan=(Button) findViewById(R.id.btnGuiLoiMoi);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading user data");
        progressDialog.setMessage("Please wait while ww load the user data.");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        current="not_friends";

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ten=snapshot.child("ten").getValue().toString();
                String email=snapshot.child("email").getValue().toString();
                String hinh=snapshot.child("hinh").getValue().toString();
                txtTenTK.setText(ten);
                txtEmail.setText(email);
                Picasso.get().load(hinh).into(imgHinh);

                RequestRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild(user_id)){
                            String request=snapshot.child(user_id).child("type").getValue().toString();
                            if(request.equals("received")){
                                current="request_received";
                                btnKetBan.setText("Chấp nhận lời mời");
                            }else if(request.equals("sent")){
                                current="request_sent";
                                btnKetBan.setText("Xóa lời mời");
                            }
                            progressDialog.dismiss();
                        }else{
                            friendRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.hasChild(user_id)){
                                        current="friends";
                                        btnKetBan.setText("HỦY KẾT BẠN");
                                    }
                                    progressDialog.dismiss();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnKetBan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnKetBan.setEnabled(false);
              if(current.equals("not_friends")) {
                  RequestRef.child(firebaseUser.getUid()).child(user_id).child("type")
                          .setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
                      @Override
                      public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                RequestRef.child(user_id).child(firebaseUser.getUid()).child("type")
                                        .setValue("received").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        //btnKetBan.setEnabled(true);
                                        current="request_sent";
                                        btnKetBan.setText("Xóa lời mời");
                                        Toasty.success(ProfileActivity.this, "Đã gửi lời mời!", Toasty.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Toasty.error(ProfileActivity.this, "Failed!", Toasty.LENGTH_SHORT).show();

                            }
                          btnKetBan.setEnabled(true);
                      }
                  });
              }

              if(current.equals("request_sent")){
                  RequestRef.child(firebaseUser.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                      @Override
                      public void onSuccess(Void unused) {
                          RequestRef.child(user_id).child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void unused) {
                                  btnKetBan.setEnabled(true);
                                  current="not_friends";
                                  btnKetBan.setText("KẾT BẠN");
                              }
                          });
                      }
                  });
              }

              if(current.equals("request_received")){
                  final String currentDate= DateFormat.getDateTimeInstance().format(new Date());
                   friendRef.child(firebaseUser.getUid()).child(user_id).setValue(currentDate)
                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   friendRef.child(user_id).child(firebaseUser.getUid()).setValue(currentDate)
                                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                                               @Override
                                               public void onSuccess(Void unused) {
                                                   RequestRef.child(firebaseUser.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                       @Override
                                                       public void onSuccess(Void unused) {
                                                           RequestRef.child(user_id).child(firebaseUser.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                               @Override
                                                               public void onSuccess(Void unused) {
                                                                   btnKetBan.setEnabled(true);
                                                                   current = "friends";
                                                                   btnKetBan.setText("HỦY KẾT BẠN");
                                                               }
                                                           });
                                                       }
                                                   });
                                               }
                                           });
                               }
                           });
              }

            }
        });
    }
}