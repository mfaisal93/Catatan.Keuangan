package com.faisal.catatankeuangan.pengeluaran;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.faisal.catatankeuangan.R;
import com.faisal.catatankeuangan.model.Pengeluaran;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class PengeluaranInputActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    TextView tanggal;
    SimpleDateFormat dateFormat;
    EditText nama, total;
    Pengeluaran pengeluaran;
    String uid;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran_input);

        getSupportActionBar().setTitle("Catatan Pengeluaran");
        dateFormat = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault());
        tanggal = findViewById(R.id.txt_date);

        uid = getIntent().getStringExtra("uid");

        nama = findViewById(R.id.nama_pengeluaran);
        total = findViewById(R.id.total_pengeluaran);
        LinearLayout btnDate = findViewById(R.id.date);
        btnDate.setOnClickListener(v -> getDate());

        Calendar currentDate = Calendar.getInstance();
        tanggal.setText(dateFormat.format(currentDate.getTime()));

        getSimpan();
    }

    private void getSimpan(){
        Button btnSimpan = findViewById(R.id.btn_simpan);
        btnSimpan.setOnClickListener(v ->{
            if (TextUtils.isEmpty(nama.getText().toString()) | TextUtils.isEmpty(total.getText().toString())) {
                Toast.makeText(PengeluaranInputActivity.this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show();
            }else {
                getData();
                getPengeluaran(pengeluaran, uid);
            }
        });
    }

    private void getPengeluaran (Pengeluaran pengeluaran, String uID) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.child(uID).child(PengeluaranListActivty.COL_PENGELUARAN).push().setValue(pengeluaran)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Data Tersimpan", Toast.LENGTH_SHORT).show());
                }


    private void getData(){
        pengeluaran = new Pengeluaran(nama.getText().toString(), Integer.parseInt(total.getText().toString()), tanggal.getText().toString());
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
