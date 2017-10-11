package com.example.rdu.booklisting;

import android.app.LoaderManager;
import android.os.Bundle;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class BookActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Book>> {

    private BookAdapter mAdapter;

    //tag for log messagess
    public static final String LOG_TAG = BookActivity.class.getSimpleName();
    private static final int BOOK_LOADER_ID = 1;
    private final String BOOKS_REQUEST_URL_BASIC = "https://www.googleapis.com/books/v1/volumes?q=";
    private TextView mEmptyTextView;
    private ArrayList<Book> listOfBooks;
    ListView bookListView;


    private static final String STATE_BOOKS = "books";
    private static final String BOOKLIST_SCROLL_POSITION = "Position of Scroll";

    @Override
    protected void onSaveInstanceState(Bundle onSaveInstanceState) {
        onSaveInstanceState.putParcelableArrayList(STATE_BOOKS, listOfBooks);
        onSaveInstanceState.putInt(BOOKLIST_SCROLL_POSITION, bookListView.getFirstVisiblePosition());
        super.onSaveInstanceState(onSaveInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        listOfBooks = savedInstanceState.getParcelableArrayList(STATE_BOOKS);
        int position = savedInstanceState.getInt(BOOKLIST_SCROLL_POSITION);
        mAdapter = new BookAdapter(BookActivity.this, listOfBooks);
        bookListView.setAdapter(mAdapter);
        bookListView.setSelection(position);

        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_main);


        bookListView = (ListView) findViewById(R.id.list);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());

        bookListView.setAdapter(mAdapter);


        //loaderManager.initLoader(BOOK_LOADER_ID, null, BookActivity.this);
        final LoaderManager loaderManager = getLoaderManager();

        //Search button which launches the loader
        ImageView searchButton = (ImageView) findViewById(R.id.search_button);
        searchButton.setClickable(true);
        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                loaderManager.restartLoader(0, null, BookActivity.this);
                //loaderManager.initLoader(BOOK_LOADER_ID, null, BookActivity.this);

                Toast.makeText(BookActivity.this,
                        "The button works",
                        Toast.LENGTH_LONG).show();

            }
        });

        //mEmptyTextView = (TextView) findViewById(R.id.empty_view);
        //bookListView.setEmptyView(mEmptyTextView);
    }


    @Override
    public Loader<ArrayList<Book>> onCreateLoader(int i, Bundle bundle) {

            String mFinalUrl = BOOKS_REQUEST_URL_BASIC + getSearchText();

        return new BookLoader(this, mFinalUrl);
    }

    //when the loader is finished display this
    @Override
    public void onLoadFinished(Loader<ArrayList<Book>> loader, ArrayList<Book> books) {
        //mEmptyTextView.setText("No books found, try searching for a different title!");
        mAdapter.clear();

        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
            listOfBooks = books;
        }
    }

    //resets the loader
    @Override
    public void onLoaderReset(Loader<ArrayList<Book>> loader) {
        mAdapter.clear();
    }

    //gets the search text from the user through the edittext search_box.

    private String getSearchText() {
        EditText searchBox = (EditText) findViewById(R.id.search_box);
        return searchBox.getText().toString().trim();
    }

}
