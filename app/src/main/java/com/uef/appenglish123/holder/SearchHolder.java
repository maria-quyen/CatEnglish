package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class SearchHolder extends RecyclerView.ViewHolder{

    TextView txtTenTV;
    public SearchHolder(@NonNull View itemView) {
        super(itemView);

       // txtTenTV=itemView.findViewById(R.id.tvSearchTenTV);

    }

    public void setTxtTenTV(String string) {
        txtTenTV.setText(string);
    }
}
