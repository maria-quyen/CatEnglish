package com.uef.appenglish123.vocabulary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.uef.appenglish123.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class VocabularyDetailActivity extends AppCompatActivity {

    private static final int REQUEST=1000;
    TextView txtMaTV, txtTuVung, txtPhienAm, txtLoaiTu, txtNghia, txtLink, txtViDu;
    ImageView  imgHinh;
    Button btnSpeaker, btnSnail, btnMicro;
    DatabaseReference databaseReference;
    MediaPlayer player;
    String slow="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_vocabulary_detail);

        txtMaTV=findViewById(R.id.txtShowMaTuVung);
        txtTuVung=findViewById(R.id.txtShowTuVung);
        txtPhienAm=findViewById(R.id.txtShowPhienAm);
        txtLoaiTu=findViewById(R.id.txtShowLoaiTu);
        txtNghia=findViewById(R.id.txtShowNghia);
        imgHinh=findViewById(R.id.imgShowHinh);
        txtLink=findViewById(R.id.txtShowLink);
        txtViDu=findViewById(R.id.txtShowViDu);

        btnMicro=findViewById(R.id.btnMicro);
        btnSpeaker=findViewById(R.id.btnPhatAm);
        btnSnail=findViewById(R.id.btnPhatAmCham);

        String id=getIntent().getStringExtra("MaTuVung");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("ListTuVung").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                txtTuVung.setText(snapshot.child("TuVung").getValue().toString());
                txtPhienAm.setText(snapshot.child("PhienAm").getValue().toString());
                txtLoaiTu.setText(Html.fromHtml("<u>"+snapshot.child("LoaiTu").getValue().toString()+"</u>"));
                txtNghia.setText(snapshot.child("Nghia").getValue().toString());
                txtViDu.setText(Html.fromHtml(snapshot.child("ViDu").getValue().toString()));
                txtLink.setText(snapshot.child("PhatAm").getValue().toString());
                slow=snapshot.child("PhatAmCham").getValue().toString();
                String hinh=snapshot.child("Hinh").getValue().toString();
                Picasso.get().load(hinh).into(imgHinh);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        btnSpeaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(txtLink.getText().toString());
                    player.prepare();
                    player.start();
                } catch (IOException ex) {
                    Toast.makeText(VocabularyDetailActivity.this, "Error!" + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    player = new MediaPlayer();
                    player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    player.setDataSource(slow);
                    player.prepare();
                    player.start();
                } catch (IOException ex) {
                    Toast.makeText(VocabularyDetailActivity.this, "Error!" + ex, Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnMicro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPhatAm();
            }
        });

    }

    private void getPhatAm() {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please pronounce...");
        try{
            startActivityForResult(intent, REQUEST);
        }
        catch(Exception ex){
            Toast.makeText(this, ""+ ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode){
            case REQUEST:{
                if(resultCode==RESULT_OK && null!=intent){
                    ArrayList<String> list=intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String phatAm=list.get(0);
                    if(!phatAm.equals(txtTuVung.getText())){
                        Toasty.error(VocabularyDetailActivity.this, "Từ "+list.get(0)+" này bạn phát âm đã sai!", Toasty.LENGTH_SHORT).show();
                    }else{
                        Toasty.success(VocabularyDetailActivity.this, "Từ "+list.get(0)+" này bạn phát âm đã đúng!", Toasty.LENGTH_SHORT).show();

                    }

                }
                break;
            }
        }
    }
}