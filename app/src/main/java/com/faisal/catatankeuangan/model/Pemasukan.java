package com.faisal.catatankeuangan.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pemasukan implements Parcelable {
    private String namaPemasukan;
    private int totalPemasukan;
    private String tanggal;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String uid) {
        this.key = uid;
    }

    public Pemasukan(String namaPemasukan, int totalPemasukan, String tanggal) {
        this.namaPemasukan = namaPemasukan;
        this.totalPemasukan = totalPemasukan;
        this.tanggal = tanggal;
    }

    public Pemasukan() {
    }


    public String getNamaPemasukan() {
        return namaPemasukan;
    }

    public void setNamaPemasukan(String namaPemasukan) {
        this.namaPemasukan = namaPemasukan;
    }

    public int getTotalPemasukan() {
        return totalPemasukan;
    }

    public void setTotalPemasukan(int totalPemasukan) {
        this.totalPemasukan = totalPemasukan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.namaPemasukan);
        dest.writeInt(this.totalPemasukan);
        dest.writeString(this.tanggal);
        dest.writeString(this.key);
    }

    protected Pemasukan(Parcel in) {
        this.namaPemasukan = in.readString();
        this.totalPemasukan = in.readInt();
        this.tanggal = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<Pemasukan> CREATOR = new Parcelable.Creator<Pemasukan>() {
        @Override
        public Pemasukan createFromParcel(Parcel source) {
            return new Pemasukan(source);
        }

        @Override
        public Pemasukan[] newArray(int size) {
            return new Pemasukan[size];
        }
    };
}
