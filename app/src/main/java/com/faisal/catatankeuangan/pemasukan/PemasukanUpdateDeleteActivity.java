package com.faisal.catatankeuangan.pemasukan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.faisal.catatankeuangan.R;
import com.faisal.catatankeuangan.model.Pemasukan;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PemasukanUpdateDeleteActivity extends AppCompatActivity {
    EditText namaPemasukan, totalPemasukan;
    TextView tanggal;
    Button btnPerbarui, btnHapus;
    DatabaseReference databaseReference;
    private FirebaseAuth auth;
    String uid;
    Pemasukan extra;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemasukan_update_delete);

        getSupportActionBar().setTitle("Catatan Pemasukan");
        extra = getIntent().getParcelableExtra("pemasukan");

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        uid = user.getUid();
        namaPemasukan = findViewById(R.id.nama_pemasukan);
        totalPemasukan = findViewById(R.id.total_pemasukan);
        tanggal = findViewById(R.id.txt_date);
        btnPerbarui = findViewById(R.id.btn_perbarui);
        btnHapus = findViewById(R.id.btn_hapus);
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        dateFormat = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault());
        tanggal = findViewById(R.id.txt_date);
        LinearLayout btnDate = findViewById(R.id.date);
        btnDate.setOnClickListener(v -> getDate());

        Calendar currentDate = Calendar.getInstance();
        tanggal.setText(dateFormat.format(currentDate.getTime()));

        getData();
        getUpdateData();
        getDeleteData();

    }

    private void getUpdateData() {

        btnPerbarui.setOnClickListener(view -> {

            if (isEmpty(namaPemasukan.getText().toString()) | isEmpty(totalPemasukan.getText().toString()) | isEmpty(tanggal.getText().toString())) {
                Toast.makeText(PemasukanUpdateDeleteActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                Pemasukan pemasukan = new Pemasukan();
                pemasukan.setNamaPemasukan(namaPemasukan.getText().toString());
                pemasukan.setTotalPemasukan(Integer.parseInt(totalPemasukan.getText().toString()));
                pemasukan.setTanggal(tanggal.getText().toString());
                databaseReference.child(uid).child(PemasukanListActivty.COL_PEMASUKAN).child(extra.getKey()).setValue(pemasukan);
                Intent intent = new Intent(PemasukanUpdateDeleteActivity.this, PemasukanListActivty.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        namaPemasukan.setText(extra.getNamaPemasukan());
        totalPemasukan.setText(String.valueOf(extra.getTotalPemasukan()));
        tanggal.setText(extra.getTanggal());
    }

    private boolean isEmpty(String s) {
        return TextUtils.isEmpty(s);
    }

    private void getDeleteData() {
        btnHapus.setOnClickListener(view -> new AlertDialog.Builder(this)
                .setTitle("Hapus Data")
                .setMessage("Apakah anda yakin ingin menghapus item ini?")
                .setPositiveButton("Ya", (dialogInterface, i) -> deleteData()).setNegativeButton("Tidak", (dialogInterface, i) -> dialogInterface.dismiss())
                .create()
                .show());
    }

    private void deleteData() {
        databaseReference.child(uid).child(PemasukanListActivty.COL_PEMASUKAN).child(extra.getKey()).removeValue()
                .addOnSuccessListener(aVoid -> {
                            Intent intent = new Intent(PemasukanUpdateDeleteActivity.this, PemasukanListActivty.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(),"Data berhasil dihapus", Toast.LENGTH_SHORT).show();
                        }
                );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getDate() {
        Calendar newDate = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {

            Calendar date = Calendar.getInstance();
            date.set(i, i1, i2);

            tanggal.setText(dateFormat.format(date.getTime()));
        }, newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
