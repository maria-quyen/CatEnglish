package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class FlashcardHolder extends RecyclerView.ViewHolder {
    public TextView txtTuVung, txtPhienAm, txtNghia, txtLinkPhatAm;
    public LinearLayout linearBefore, linearAfter;
    public Button btnNext;

    public FlashcardHolder(@NonNull View itemView) {
        super(itemView);
        linearBefore=itemView.findViewById(R.id.linearBefore);;
        linearAfter=itemView.findViewById(R.id.linearAfter);
        btnNext=itemView.findViewById(R.id.btnNextFc);
        txtTuVung = itemView.findViewById(R.id.txtVocab);
        txtPhienAm = itemView.findViewById(R.id.txtSpelling);
        txtNghia = itemView.findViewById(R.id.txtMeaning);
        txtLinkPhatAm=itemView.findViewById(R.id.txtLink);
    }


    public void setTxtTuVung(String string) {
        txtTuVung.setText(string);
    }
    public void setTxtPhienAm(String string) {
        txtPhienAm.setText(string);
    }
    public void setTxtNghia(String string) {
        txtNghia.setText(string);
    }
    public void setTxtLinkPhatAm(String string) {
        txtLinkPhatAm.setText(string);
    }
}
