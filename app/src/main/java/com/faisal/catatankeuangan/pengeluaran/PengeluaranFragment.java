package com.faisal.catatankeuangan.pengeluaran;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.faisal.catatankeuangan.R;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 */
public class PengeluaranFragment extends Fragment {
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;

    public PengeluaranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pengeluaran, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
    super.onViewCreated(view, savedInstanceState);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        Button btnCatatanPemasukan = view.findViewById(R.id.btn_catatan_pemasukan);
        Button btnDaftarPemasukan = view.findViewById(R.id.btn_list_pemasukan);
        btnCatatanPemasukan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PengeluaranInputActivity.class);
            intent.putExtra("uid", user.getUid());
            startActivity(intent);
        });

        btnDaftarPemasukan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PengeluaranListActivty.class);
            startActivity(intent);
        });
    }

}
