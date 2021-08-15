package com.uef.appenglish123.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
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
import com.uef.appenglish123.holder.BinhLuanHolder;
import com.uef.appenglish123.model.BinhLuan;
import com.uef.appenglish123.model.TaiKhoan;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class ViewDetailActivity extends AppCompatActivity {

    TextView tvTenNP, tvCTNP;
    TextView editComment;
    Button btnShow, btnBack;
    ScrollView scrollView;
    LinearLayout linearLayout;
    FirebaseUser firebaseUser;
    DatabaseReference dataNguPhap, dataBinhLuan, dataTaiKhoan;
    String show="show";
    String id="";
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseRecyclerAdapter<BinhLuan, BinhLuanHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_view_detail);

        tvTenNP=findViewById(R.id.tvTenNguPhap);
        tvCTNP=findViewById(R.id.tvChiTietNguPhap);
        linearLayout=findViewById(R.id.linearComment);
        btnShow=findViewById(R.id.btnShowComment);
        btnBack=findViewById(R.id.backHome);
        scrollView=findViewById(R.id.scrollView);
        editComment=findViewById(R.id.editComment);
        linearLayout.setVisibility(View.INVISIBLE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show.equals("show")) {
                    linearLayout.setVisibility(View.VISIBLE);
                    scrollView.setVisibility(View.GONE);
                    btnShow.setText("Ẩn bình luận");
                    show="hidden";
                }else
                if(show.equals("hidden")) {
                    linearLayout.setVisibility(View.INVISIBLE);
                    scrollView.setVisibility(View.VISIBLE);
                    btnShow.setText("Hiện bình luận");
                    show="show";
                }
            }
        });

        id=getIntent().getStringExtra("MaNP");
        dataNguPhap= FirebaseDatabase.getInstance().getReference().child("NguPhap").child(id);
        dataNguPhap.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvTenNP.setText(snapshot.child("TenNP").getValue().toString());
                tvCTNP.setText(Html.fromHtml(snapshot.child("NoiDung").getValue().toString()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        editComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogEdit=new Dialog(ViewDetailActivity.this);
                dialogEdit.setCancelable(false);
                dialogEdit.setContentView(R.layout.dialog_edit_comment);

                final EditText txtComment=(EditText) dialogEdit.findViewById(R.id.tvEditComment);
                final Button btnChange=(Button) dialogEdit.findViewById(R.id.btnUpdateComment);
                final Button btnCancel=(Button) dialogEdit.findViewById(R.id.btnCancelComment);
                btnChange.setText("send");

                btnChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(txtComment.getText().length()==0){
                            txtComment.setError("Vui lòng nhập bình luận!");
                            txtComment.requestFocus();
                            return;
                        }else{
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            dataBinhLuan= FirebaseDatabase.getInstance().getReference().child("BinhLuan").push();
                            Map<String,Object> map = new HashMap<>();
                            map.put("MaBL", dataBinhLuan.getKey());
                            map.put("MaNP", id);
                            map.put("MaTK", firebaseUser.getUid());
                            map.put("NoiDung", txtComment.getText().toString());
                            dataBinhLuan.setValue(map);
                            txtComment.setText("");
                            dialogEdit.cancel();
                            Toasty.success(ViewDetailActivity.this, "Gửi bình luận thành công!", Toasty.LENGTH_SHORT).show();
                        }


                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogEdit.cancel();
                    }
                });
                dialogEdit.show();
            }
        });

        recyclerView = findViewById(R.id.recyclerComment);
        dataBinhLuan = FirebaseDatabase.getInstance().getReference("BinhLuan");

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadBinhLuan();

    }

    private void loadBinhLuan(){
        Query query = dataBinhLuan.orderByChild("MaNP").equalTo(id);
        FirebaseRecyclerOptions<BinhLuan> options = new FirebaseRecyclerOptions.Builder<BinhLuan>()
                .setQuery(query, new SnapshotParser<BinhLuan>() {
                    @NonNull
                    @Override
                    public BinhLuan parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return new BinhLuan(
                                snapshot.child("MaNP").getValue().toString(),
                                snapshot.child("MaTK").getValue().toString(),
                                snapshot.child("NoiDung").getValue().toString());
                    }
                })
                .build();

        adapter = new FirebaseRecyclerAdapter<BinhLuan, BinhLuanHolder>(options) {
            @Override
            public BinhLuanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_comment, parent, false);
                return new BinhLuanHolder(view);
            }


            @Override
            protected void onBindViewHolder(BinhLuanHolder holder, final int position, BinhLuan model) {
                holder.tvNoiDungBL.setText(model.getNoiDung());
                String idUser = model.getMaTK();
                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                dataTaiKhoan = FirebaseDatabase.getInstance().getReference("Account").child(idUser);
                dataTaiKhoan.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        TaiKhoan tk = snapshot.getValue(TaiKhoan.class);
                        holder.tvTenTK.setText(tk.getTen());
                        Picasso.get().load(tk.getHinh()).into(holder.riv);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getMaTK()==firebaseUser.getUid()) {
                            CharSequence option[] = new CharSequence[]{"Sửa bình luận","Xóa bình luận"};
                            final AlertDialog.Builder builder = new AlertDialog.Builder(ViewDetailActivity.this);
                            builder.setItems(option, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    if (i == 0) {
                                        final Dialog dialogEdit=new Dialog(ViewDetailActivity.this);
                                        dialogEdit.setCancelable(false);
                                        dialogEdit.setContentView(R.layout.dialog_edit_comment);

                                        final EditText txtComment=(EditText) dialogEdit.findViewById(R.id.tvEditComment);
                                        final Button btnChange=(Button) dialogEdit.findViewById(R.id.btnUpdateComment);
                                        final Button btnCancel=(Button) dialogEdit.findViewById(R.id.btnCancelComment);
                                        txtComment.setText(holder.tvNoiDungBL.getText().toString());

                                        btnChange.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String cmt=txtComment.getText().toString();
                                                if(cmt.isEmpty()){
                                                    txtComment.setError("Please do not leave this field blank!");
                                                    txtComment.requestFocus();
                                                    return;
                                                }else{
                                                    dataBinhLuan.child(getRef(position).getKey());
                                                    HashMap<String, Object> map = new HashMap<>();
                                                    map.put("NoiDung", txtComment.getText().toString());
                                                    dataBinhLuan.updateChildren(map);
                                                    dialogEdit.cancel();
                                                    Toasty.success(ViewDetailActivity.this, "Sửa bình luận thành công!", Toasty.LENGTH_SHORT).show();


                                                }
                                            }
                                        });

                                        btnCancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialogEdit.cancel();
                                            }
                                        });
                                        dialogEdit.show();
                                    }
                                    if (i == 1) {
                                        FirebaseDatabase.getInstance().getReference().child("BinhLuan")
                                                .child(getRef(position).getKey()).removeValue();
                                    }
                                }
                            });
                            builder.show();
                        }

                    }
                });

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}