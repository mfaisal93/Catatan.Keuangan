package com.faisal.catatankeuangan.pengeluaran;

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
import com.faisal.catatankeuangan.model.Pengeluaran;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranAdapter.PengeluaranViewHolder> {
    private ValueEventListener eventListener;
    private ArrayList<Pengeluaran> pengeluaranList;
    Context context;


    public PengeluaranAdapter(ArrayList<Pengeluaran> pengeluaranList, ValueEventListener eventListener) {
        this.pengeluaranList = pengeluaranList;
        this.eventListener = eventListener;
    }

    @NonNull
    @Override
    public PengeluaranAdapter.PengeluaranViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pengeluaran, viewGroup, false);
        return new PengeluaranViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PengeluaranAdapter.PengeluaranViewHolder pengeluaranView, int i) {
        final Pengeluaran pengeluaran = pengeluaranList.get(i);

        pengeluaranView.namaPengeluaran.setText(pengeluaran.getNamaPengeluaran());
        pengeluaranView.totalPengeluaran.setText(String.valueOf(pengeluaran.getTotalPengeluaran()));
        pengeluaranView.tanggal.setText(pengeluaran.getTanggal());
        pengeluaranView.toDetail.setOnClickListener(v ->{
            Intent intent = new Intent(context, PengeluaranUpdateDeleteActivity.class);
            intent.putExtra("pengeluaran",pengeluaran);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return pengeluaranList.size();
    }

    class PengeluaranViewHolder extends RecyclerView.ViewHolder {
        private TextView namaPengeluaran;
        private TextView totalPengeluaran;
        private TextView tanggal;
        private CardView toDetail;
        PengeluaranViewHolder(@NonNull View itemView) {
            super(itemView);
            namaPengeluaran = itemView.findViewById(R.id.txt_nama_pengeluaran);
            totalPengeluaran = itemView.findViewById(R.id.txt_total_pengeluaran);
            tanggal = itemView.findViewById(R.id.txt_tanggal_pengeluaran);
            toDetail = itemView.findViewById(R.id.header);
            context = itemView.getContext();
        }
    }
}
