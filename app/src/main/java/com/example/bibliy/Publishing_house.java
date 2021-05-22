package com.example.bibliy;

import android.os.Parcel;
import android.os.Parcelable;

public class Publishing_house implements Parcelable {
    String publisher_name, city_full, city_less;
    Integer id;

    public Publishing_house(Integer id, String publisher_name, String city_full, String city_less) {
        this.id = id;
        this.publisher_name = publisher_name;
        this.city_full = city_full;
        this.city_less = city_less;
    }

    public Publishing_house(Parcel in) {
        id = in.readInt();
        publisher_name = in.readString();
        city_full = in.readString();
        city_less = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.publisher_name);
        out.writeString(this.city_full);
        out.writeString(this.city_less);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublisher_name() {
        return publisher_name;
    }

    public void setPublisher_name(String publisher_name) {
        this.publisher_name = publisher_name;
    }

    public String getCity_full() {
        return city_full;
    }

    public void setCity_full(String city_full) {
        this.city_full = city_full;
    }

    public String getCity_less() {
        return city_less;
    }

    public void setCity_less(String city_less) {
        this.city_less = city_less;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Publishing_house> CREATOR = new Parcelable.Creator<Publishing_house>() {
        @Override
        public Publishing_house createFromParcel(Parcel source) {
            return new Publishing_house(source);
        }

        @Override
        public Publishing_house[] newArray(int size) {
            return new Publishing_house[size];
        }
    };
}
