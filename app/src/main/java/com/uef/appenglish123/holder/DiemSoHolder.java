package com.uef.appenglish123.holder;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uef.appenglish123.R;

public class DiemSoHolder extends RecyclerView.ViewHolder {
    public TextView tvTenBaiTest;
    public ProgressBar progressBar;
    public DiemSoHolder(@NonNull View itemView) {
        super(itemView);

        tvTenBaiTest=itemView.findViewById(R.id.tvTenBaiTest);
        progressBar=itemView.findViewById(R.id.progressBarScore);
    }
}
