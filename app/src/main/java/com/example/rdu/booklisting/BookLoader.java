package com.example.rdu.booklisting;


import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * Created by Rdu on 05.10.2017.
 */

public class BookLoader extends AsyncTaskLoader<ArrayList<Book>> {

    private String mUrl;

    public BookLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        ArrayList<Book> books = QueryUtils.fetchBooks(mUrl);
        return books;
    }
}
