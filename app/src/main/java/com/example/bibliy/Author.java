package com.example.bibliy;

import android.os.Parcel;
import android.os.Parcelable;

public class Author implements Parcelable {
    String first_name, last_name, middle_name;
    Integer id;

    public Author(Integer id, String first_name, String last_name, String middle_name) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.middle_name = middle_name;
    }

    public Author(Parcel in) {
        id = in.readInt();
        first_name = in.readString();
        last_name = in.readString();
        middle_name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.first_name);
        out.writeString(this.last_name);
        out.writeString(this.middle_name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Author> CREATOR = new Parcelable.Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel source) {
            return new Author(source);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };
}
