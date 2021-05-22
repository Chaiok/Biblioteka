package com.example.bibliy;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    String book_name, genre, year_of_publishing;
    Integer id, pages, id_author, id_publishing_house;

    public Book(Integer id, String book_name, String genre, String year_of_publishing, Integer pages, Integer id_author, Integer id_publishing_house) {
        this.id = id;
        this.book_name = book_name;
        this.genre = genre;
        this.year_of_publishing = year_of_publishing;
        this.pages = pages;
        this.id_author = id_author;
        this.id_publishing_house = id_publishing_house;
    }

    public Book(Parcel in) {
        id = in.readInt();
        book_name = in.readString();
        genre = in.readString();
        year_of_publishing = in.readString();
        pages = in.readInt();
        id_author = in.readInt();
        id_publishing_house = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.id);
        out.writeString(this.book_name);
        out.writeString(this.genre);
        out.writeString(this.year_of_publishing);
        out.writeInt(this.pages);
        out.writeInt(this.id_author);
        out.writeInt(this.id_publishing_house);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getYear_of_publishing() {
        return year_of_publishing;
    }

    public void setYear_of_publishing(String year_of_publishing) {
        this.year_of_publishing = year_of_publishing;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getId_author() {
        return id_author;
    }

    public void setId_author(Integer id_author) {
        this.id_author = id_author;
    }

    public Integer getId_publishing_house() {
        return id_publishing_house;
    }

    public void setId_publishing_house(Integer id_publishing_house) {
        this.id_publishing_house = id_publishing_house;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
