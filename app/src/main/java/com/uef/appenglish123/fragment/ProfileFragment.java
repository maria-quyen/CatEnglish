package com.uef.appenglish123.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.R;
import com.uef.appenglish123.start.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;


public class ProfileFragment extends Fragment {


    FirebaseAuth firebaseAuth;
    FloatingActionButton floatProfile;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    TextView tvTenTK, tvSDT, tvEmail, tvGioiTinh;
    RoundedImageView rivHinh;
    String gt="";
    String mail="", mk="";

    public ProfileFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        floatProfile=view.findViewById(R.id.floatChangeInfo);
        tvTenTK=view.findViewById(R.id.tv_profileTenTK);
        tvSDT=view.findViewById(R.id.tv_profileSDT);
        tvEmail=view.findViewById(R.id.tv_profileEmail);
        tvGioiTinh=view.findViewById(R.id.tv_profileGioiTinh);
        rivHinh=view.findViewById(R.id.riv_profileHinh);
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth=FirebaseAuth.getInstance();
        String id=firebaseUser.getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Account").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("ten").getValue().toString();
                String sdt=snapshot.child("sdt").getValue().toString();
                mail=snapshot.child("email").getValue().toString();
                String gt=snapshot.child("gioiTinh").getValue().toString();
                String hinh=snapshot.child("hinh").getValue().toString();
                mk=snapshot.child("matKhau").getValue().toString();
                tvTenTK.setText(name);
                tvEmail.setText(mail);
                tvSDT.setText(sdt);
                if(gt.equals("Male")){
                    tvGioiTinh.setText("Nam");
                }else{
                    tvGioiTinh.setText("Nữ");
                }
                Picasso.get().load(hinh).into(rivHinh);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        XuLuNutFloat();
        return view;
    }

   private void XuLuNutFloat(){
        floatProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence option[]=new CharSequence[]{"Đổi mật khẩu","Đăng xuất"};
                final android.app.AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
                builder.setTitle("Chọn một mục");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if(i==0){
                            final Dialog dialogEdit=new Dialog(getActivity());
                            dialogEdit.setCancelable(false);
                            dialogEdit.setContentView(R.layout.dialog_change_password);

                            final TextInputEditText txtMKC=(TextInputEditText) dialogEdit.findViewById(R.id.txtMKCu);
                            final TextInputEditText txtMKM=(TextInputEditText) dialogEdit.findViewById(R.id.txtMKM);
                            final TextInputEditText txtXNMK=(TextInputEditText) dialogEdit.findViewById(R.id.txtXNMK);
                            final Button btnChange=(Button) dialogEdit.findViewById(R.id.btnChangePass);
                            final Button btnCancel=(Button) dialogEdit.findViewById(R.id.btnCancelChangePass);

                            btnChange.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String mkc=txtMKC.getEditableText().toString();
                                    String mkm=txtMKM.getEditableText().toString();
                                    String xnmk=txtXNMK.getEditableText().toString();
                                    if(mkc.isEmpty()){
                                        txtMKC.setError("Please do not leave this field blank!");
                                        txtMKC.requestFocus();
                                        return;
                                    }else if(!mkc.equals(mk)){
                                        txtMKC.setError("Old password is wrong!");
                                        txtMKC.requestFocus();
                                        return;
                                    }else if(mkm.isEmpty()){
                                        txtMKM.setError("Please do not leave this field blank!");
                                        txtMKM.requestFocus();
                                        return;
                                    }else if(mkm.length()<8){
                                        txtMKM.setError("Password please be at least 8 characters!");
                                        txtMKM.requestFocus();
                                        return;
                                    }else if(mkm.equals(mkc)){
                                        txtMKM.setError("Please enter a new password that is different from the old password!");
                                        txtMKM.requestFocus();
                                        return;
                                    }else if(!xnmk.equals(mkm)){
                                        txtMKM.setError("Please confirm the correct password!");
                                        txtMKM.requestFocus();
                                        return;
                                    }else{
                                        AuthCredential credential= EmailAuthProvider.getCredential(mail, mk);
                                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    firebaseUser.updatePassword(txtMKM.getEditableText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                               Toasty.success(getContext(),"Nhập lại mật khẩu thành công!", Toasty.LENGTH_SHORT).show();
                                                                dialogEdit.cancel();
                                                            } else {
                                                                Toasty.error(getContext(),"Xảy ra lỗi, vui lòng thử lại!", Toasty.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toasty.error(getContext(),"Lỗi, vui lòng thử lại!", Toasty.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                        databaseReference = FirebaseDatabase.getInstance().getReference("Account")
                                                .child(firebaseUser.getUid());
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("matKhau", txtMKM.getEditableText().toString());
                                        databaseReference.updateChildren(map);
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
                        if(i==1){
                            AlertDialog.Builder builderLogOut=new AlertDialog.Builder(getActivity());
                            builderLogOut.setTitle("THÔNG BÁO");
                            builderLogOut.setMessage("Bạn có muốn đăng xuất không?");
                            builderLogOut.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    firebaseAuth.signOut();
                                    startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                }
                            });

                            builderLogOut.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builderLogOut.show();

                        }
                    }
                });
                builder.show();
            }
        });
   }
}

/* Dialog dialogUpdate=new Dialog(getActivity());
                            dialogUpdate.setCancelable(false);
                            dialogUpdate.setContentView(R.layout.dialog_change_profile);

                            final TextInputEditText txtTenTK=(TextInputEditText) dialogUpdate.findViewById(R.id.txtSuaTenTK);
                            final TextInputEditText txtSDT=(TextInputEditText) dialogUpdate.findViewById(R.id.txtSuaSDT);
                            final CheckBox checkMale=(CheckBox) dialogUpdate.findViewById(R.id.cbMale);
                            final CheckBox checkFemale=(CheckBox) dialogUpdate.findViewById(R.id.cbFemale);
                            final Button btnChange=(Button) dialogUpdate.findViewById(R.id.btnChange);
                            final Button btnCancel=(Button) dialogUpdate.findViewById(R.id.btnTatDialog);
                            txtTenTK.setText(tvTenTK.getText());
                            txtSDT.setText(tvSDT.getText());
                            if(tvGioiTinh.getText().equals("Male")){
                                checkMale.setChecked(true);
                                gt="Male";
                                return;
                            }
                            if(tvGioiTinh.getText().equals("Female")){
                                checkFemale.setChecked(true);
                                gt="Female";
                            }

                            checkMale.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkMale.setChecked(true);
                                    checkFemale.setChecked(false);
                                    gt ="Male";
                                }
                            });

                            checkFemale.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    checkFemale.setChecked(true);
                                    checkMale.setChecked(false);
                                    gt ="Female";
                                }
                            });
                            btnChange.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(txtTenTK.getEditableText().equals("")||txtSDT.getEditableText().equals("")){
                                        Toasty.warning(getContext(),"Vui lòng không bỏ trống thông tin nào!").show();
                                    }else {
                                        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                        databaseReference = FirebaseDatabase.getInstance().getReference("Account")
                                                .child(firebaseUser.getUid());

                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("ten", txtTenTK.getEditableText().toString());
                                        map.put("sdt", txtSDT.getEditableText().toString());
                                        map.put("gioiTinh", gt);
                                        databaseReference.updateChildren(map);
                                        Toasty.success(getContext(), "Đổi thông tin thành công!", Toasty.LENGTH_SHORT).show();
                                        dialogUpdate.cancel();
                                    }
                                }
                            });

                            btnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialogUpdate.cancel();
                                }
                            });
                            dialogUpdate.show();*/