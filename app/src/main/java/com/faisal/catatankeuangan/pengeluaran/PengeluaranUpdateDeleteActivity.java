package com.faisal.catatankeuangan.pengeluaran;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.faisal.catatankeuangan.R;
import com.faisal.catatankeuangan.model.Pengeluaran;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class PengeluaranUpdateDeleteActivity extends AppCompatActivity {
    EditText namaPengeluaran, totalPengeluaran;
    TextView tanggal;
    Button btnPerbarui, btnHapus;
    DatabaseReference databaseReference;
    private FirebaseAuth auth;
    String uid;
    Pengeluaran extra;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran_update_delete);

        getSupportActionBar().setTitle("Catatan Pengeluaran");
        extra = getIntent().getParcelableExtra("pengeluaran");

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        uid = user.getUid();
        namaPengeluaran = findViewById(R.id.nama_pengeluaran);
        totalPengeluaran = findViewById(R.id.total_pengeluaran);
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

            if (isEmpty(namaPengeluaran.getText().toString()) | isEmpty(totalPengeluaran.getText().toString()) | isEmpty(tanggal.getText().toString())) {
                Toast.makeText(PengeluaranUpdateDeleteActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
            } else {
                Pengeluaran pengeluaran = new Pengeluaran();
                pengeluaran.setNamaPengeluaran(namaPengeluaran.getText().toString());
                pengeluaran.setTotalPengeluaran(Integer.parseInt(totalPengeluaran.getText().toString()));
                pengeluaran.setTanggal(tanggal.getText().toString());
                databaseReference.child(uid).child(PengeluaranListActivty.COL_PENGELUARAN).child(extra.getKey()).setValue(pengeluaran);
                Intent intent = new Intent(PengeluaranUpdateDeleteActivity.this, PengeluaranListActivty.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getData() {
        namaPengeluaran.setText(extra.getNamaPengeluaran());
        totalPengeluaran.setText(String.valueOf(extra.getTotalPengeluaran()));
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
        databaseReference.child(uid).child(PengeluaranListActivty.COL_PENGELUARAN).child(extra.getKey()).removeValue()
                .addOnSuccessListener(aVoid -> {
                            Intent intent = new Intent(PengeluaranUpdateDeleteActivity.this, PengeluaranListActivty.class);
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
