package com.faisal.catatankeuangan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pengeluaran implements Parcelable {
    private String namaPengeluaran;
    private int totalPengeluaran;
    private String tanggal;
    private String key;

    public Pengeluaran(String namaPengeluaran, int totalPengeluaran, String tanggal) {
        this.namaPengeluaran = namaPengeluaran;
        this.totalPengeluaran = totalPengeluaran;
        this.tanggal = tanggal;
    }

    public String getNamaPengeluaran() {
        return namaPengeluaran;
    }

    public void setNamaPengeluaran(String namaPengeluaran) {
        this.namaPengeluaran = namaPengeluaran;
    }

    public int getTotalPengeluaran() {
        return totalPengeluaran;
    }

    public void setTotalPengeluaran(int totalPengeluaran) {
        this.totalPengeluaran = totalPengeluaran;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namaPengeluaran);
        dest.writeInt(this.totalPengeluaran);
        dest.writeString(this.tanggal);
        dest.writeString(this.key);
    }

    public Pengeluaran() {
    }

    protected Pengeluaran(Parcel in) {
        this.namaPengeluaran = in.readString();
        this.totalPengeluaran = in.readInt();
        this.tanggal = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<Pengeluaran> CREATOR = new Parcelable.Creator<Pengeluaran>() {
        @Override
        public Pengeluaran createFromParcel(Parcel source) {
            return new Pengeluaran(source);
        }

        @Override
        public Pengeluaran[] newArray(int size) {
            return new Pengeluaran[size];
        }
    };
}
