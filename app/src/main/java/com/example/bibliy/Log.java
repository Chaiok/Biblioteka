package com.example.bibliy;

import android.os.Parcel;
import android.os.Parcelable;

public class Log implements Parcelable {
    String log;
    Integer id, id_librarian, id_book, id_client;

    public Log(Integer id, String log, Integer id_client, Integer id_book, Integer id_librarian) {
        this.id = id;
        this.log = log;
        this.id_client = id_client;
        this.id_book = id_book;
        this.id_librarian = id_librarian;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_librarian() {
        return id_librarian;
    }

    public void setId_librarian(Integer id_librarian) {
        this.id_librarian = id_librarian;
    }

    public Integer getId_book() {
        return id_book;
    }

    public void setId_book(Integer id_book) {
        this.id_book = id_book;
    }

    public Integer getId_client() {
        return id_client;
    }

    public void setId_client(Integer id_client) {
        this.id_client = id_client;
    }

    public Log(Parcel in) {
        id = in.readInt();
        log = in.readString();
        id_client = in.readInt();
        id_book = in.readInt();
        id_librarian = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.log);
        out.writeInt(this.id_client);
        out.writeInt(this.id_book);
        out.writeInt(this.id_librarian);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Log> CREATOR = new Parcelable.Creator<Log>() {
        @Override
        public Log createFromParcel(Parcel source) {
            return new Log(source);
        }

        @Override
        public Log[] newArray(int size) {
            return new Log[size];
        }
    };
}
