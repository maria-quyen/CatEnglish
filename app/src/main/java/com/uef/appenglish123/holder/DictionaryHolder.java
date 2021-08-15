package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class DictionaryHolder extends RecyclerView.ViewHolder {
    public TextView txtTuVung;


    public DictionaryHolder(@NonNull View itemView) {
        super(itemView);

        txtTuVung = itemView.findViewById(R.id.tvTuVungDic);
    }

    public void setTxtTuVung(String string) {
        txtTuVung.setText(string);
    }
}
