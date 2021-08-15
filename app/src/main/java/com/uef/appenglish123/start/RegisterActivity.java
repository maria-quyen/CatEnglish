package com.uef.appenglish123.start;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;
import com.uef.appenglish123.R;
import com.uef.appenglish123.fragment.HomeActivity;
import com.uef.appenglish123.model.TaiKhoan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity{

    TextInputEditText txtTen, txtSDT, txtEmail, txtMatKhau, txtNLMatKhau;
    TextView txtQuyen, txtHinh;
    CheckBox chbMale, chbFemale;
    Button btnSignup;
    ImageView imgBack;
    Matcher matcher;
    Pattern pattern;
    private FirebaseAuth firebaseAuth;
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register);

        //Ánh xạ
        txtTen=(TextInputEditText) findViewById(R.id.txtUsername);
        chbMale=(CheckBox) findViewById(R.id.chbMale);
        chbFemale=(CheckBox) findViewById(R.id.chbFemale);
        txtSDT=(TextInputEditText) findViewById(R.id.txtPhoneNumber);
        txtEmail=(TextInputEditText) findViewById(R.id.txtEmailAddress);
        txtMatKhau=(TextInputEditText) findViewById(R.id.txtPassword);
        txtNLMatKhau=(TextInputEditText) findViewById(R.id.txtConfirmPassword);
        txtQuyen=(TextView) findViewById(R.id.txtAccessRights);
        txtHinh=(TextView) findViewById(R.id.txtImage);
        btnSignup=(Button) findViewById(R.id.btnCreate);

        CheckBoxClick();
        btnSignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerButton();
            }
        });


    }
    public RegisterActivity() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    // Class kiểm định dạng email
    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

    private void registerButton(){
        RegisterActivity validator = new RegisterActivity();
        String ten=txtTen.getEditableText().toString();
        String sdt=txtSDT.getEditableText().toString();
        String status="offline";
        String gt="";
        String hinhnen="https://i.pinimg.com/originals/bb/2f/75/bb2f7578d01d34fc9bf0ae02ed15a5ed.jpg";
        if(chbMale.isChecked()){
            gt=chbMale.getText().toString().trim();
        }else if(chbFemale.isChecked()){
            gt=chbFemale.getText().toString().trim();
        }
        String email=txtEmail.getEditableText().toString();
        String pass=txtMatKhau.getEditableText().toString();
        String repass=txtNLMatKhau.getEditableText().toString();
        String hinh=txtHinh.getText().toString().trim();

        if(ten.isEmpty()){
            txtTen.setError("Please fill in this field!");
            txtTen.requestFocus();
            return;
        }else
        if(!chbMale.isChecked()&&!chbFemale.isChecked()){
           Toasty.error(RegisterActivity.this, "Please select gender!", Toast.LENGTH_SHORT, true).show();
        }else
        if(sdt.isEmpty()){
            txtSDT.setError("Please fill in this field!");
            txtSDT.requestFocus();
            return;
        }else
        if(sdt.length()<10){
            txtMatKhau.setError("Please enter a phone number of at least 8 characters!");
            txtMatKhau.requestFocus();
            return;
        }else
        if(email.isEmpty()){
            txtEmail.setError("Please fill in this field!");
            txtEmail.requestFocus();
            return;
        }else
        if (!validator.validate(email)) {
            txtEmail.setError("Invalid email format!");
            txtEmail.requestFocus();
            return;
        }else
        if(pass.isEmpty()){
            txtMatKhau.setError("Please fill in this field!");
            txtMatKhau.requestFocus();
            return;
        }else
        if(pass.length()<8){
            txtMatKhau.setError("Please enter a password of at least 8 characters!");
            txtMatKhau.requestFocus();
            return;
        }else
        if(repass.isEmpty()){
            txtNLMatKhau.setError("Please fill in this field!");
            txtNLMatKhau.requestFocus();
            return;
        }else
        if(!repass.equals(pass)){
            txtNLMatKhau.setError("Wrong password re-entered!");
            txtNLMatKhau.requestFocus();
            return;
        }else{
            validateDetail(ten, gt, sdt, email, pass, hinh, status, hinhnen);
        }
    }

    private void validateDetail(final String Ten, final String GioiTinh, final String SDT, final String email, final String pass, final String Hinh, final String Status, final String hinhnen) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toasty.error(RegisterActivity.this, "Email này đã được sử dụng!", Toasty.LENGTH_SHORT).show();
                    }
                }else{
                    TaiKhoan tk = new TaiKhoan(Ten, GioiTinh, SDT, email, pass, Hinh, Status, hinhnen);
                    FirebaseDatabase.getInstance().getReference("Account")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(tk).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toasty.success(RegisterActivity.this, "You have successfully registered!", Toast.LENGTH_SHORT, true).show();
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                startActivity(intent);
                            } else {
                                Toasty.error(RegisterActivity.this, "Failed to register! Try again!", Toasty.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

    }


    private void CheckBoxClick() {
        chbMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chbMale.setChecked(true);
                chbFemale.setChecked(false);
            }
        });

        chbFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chbMale.setChecked(false);
                chbFemale.setChecked(true);
            }
        });
    }


}
