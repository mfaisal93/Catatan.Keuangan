package com.faisal.catatankeuangan.pemasukan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.faisal.catatankeuangan.R;
import com.faisal.catatankeuangan.model.Pemasukan;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PemasukanAdapter extends RecyclerView.Adapter<PemasukanAdapter.PemasukanViewHolder> {
    private ValueEventListener eventListener;
    private ArrayList<Pemasukan> pemasukanList;
    Context context;


    public PemasukanAdapter(ArrayList<Pemasukan> pemasukanList, ValueEventListener eventListener) {
        this.pemasukanList = pemasukanList;
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public PemasukanAdapter.PemasukanViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new PemasukanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PemasukanAdapter.PemasukanViewHolder pemasukanView, int i) {
        final Pemasukan pemasukan = pemasukanList.get(i);

        pemasukanView.namaPemasukan.setText(pemasukan.getNamaPemasukan());
        pemasukanView.totalPemasukan.setText(String.valueOf(pemasukan.getTotalPemasukan()));
        pemasukanView.tanggal.setText(pemasukan.getTanggal());
        pemasukanView.toDetail.setOnClickListener(v ->{
            Intent intent = new Intent(context, PemasukanUpdateDeleteActivity.class);
            intent.putExtra("pemasukan",pemasukan);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pemasukanList.size();
    }

    class PemasukanViewHolder extends RecyclerView.ViewHolder {
        private TextView namaPemasukan;
        private TextView totalPemasukan;
        private TextView tanggal;
        private CardView toDetail;
        PemasukanViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPemasukan = itemView.findViewById(R.id.txt_nama_Pemasukan);
            totalPemasukan = itemView.findViewById(R.id.txt_total_pemasukan);
            tanggal = itemView.findViewById(R.id.txt_tanggal_pemasukan);
            toDetail = itemView.findViewById(R.id.header);
            context = itemView.getContext();
        }
    }
}
