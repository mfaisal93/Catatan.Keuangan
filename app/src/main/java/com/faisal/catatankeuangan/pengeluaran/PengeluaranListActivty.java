package com.faisal.catatankeuangan.pengeluaran;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faisal.catatankeuangan.MainActivity;
import com.faisal.catatankeuangan.R;
import com.faisal.catatankeuangan.model.Pengeluaran;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PengeluaranListActivty extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private PengeluaranAdapter adapter;
    private ArrayList<Pengeluaran> pengeluaranList;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    public static final String COL_PENGELUARAN = "CatatanPengeluaran";
    private FirebaseAuth auth;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan_list_activty);

        getSupportActionBar().setTitle("Catatan Pengeluaran");
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        uid = user.getUid();

        progressBar = findViewById(R.id.progress_list);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar.setVisibility(View.VISIBLE);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        databaseReference.child(uid).child(COL_PENGELUARAN)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        pengeluaranList = new ArrayList<>();

                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Pengeluaran pengeluaran = data.getValue(Pengeluaran.class);
                            if (pengeluaran != null) {
                                pengeluaran.setKey(data.getKey());
                                pengeluaranList.add(pengeluaran);
                                progressBar.setVisibility(View.GONE);
                            }

                        }

                        adapter = new PengeluaranAdapter(pengeluaranList, this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    public void onBackPressed() {
        Intent exit = new Intent(getApplicationContext(), MainActivity.class);
        exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(exit);
    }
}
