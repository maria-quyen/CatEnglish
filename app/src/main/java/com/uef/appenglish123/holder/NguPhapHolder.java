package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class NguPhapHolder extends RecyclerView.ViewHolder {
    public TextView txtTenNP;

    public NguPhapHolder(@NonNull View itemView) {
        super(itemView);
        txtTenNP = (TextView) itemView.findViewById(R.id.txtTenNP);
    }

}
