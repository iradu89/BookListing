package com.example.rdu.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Rdu on 04.10.2017.
 */

public class Book implements Parcelable {

    //Author of the book
    private String mAuthor;

    //Title of the book
    private String mTitle;

    //Description of the book
    private String mDescription;

    //constructor with the 3 params described above
    public Book(String author, String title, String description){
        mAuthor = author;
        mTitle = title;
        mDescription = description;
    }

    //Returns the Author name
    public String getAuthor(){
        return mAuthor;
    }

    //Returns the book Title
    public String getTitle(){
        return mTitle;
    }

    //Returns the description
    public String getDescription(){
        return mDescription;
    }

    protected Book(Parcel in) {
        mAuthor = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mAuthor);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
}
