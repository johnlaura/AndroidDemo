package com.cyberpunk27area.processconnection3;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    private String title;
    private String content;

    public Book(String title, String content) {
        this.title = title;
        this.content = content;
    }

    protected Book(Parcel in) {
        title = in.readString();
        content = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
    }
}
