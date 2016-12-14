package com.example.android.booklistingapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getName();
    final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private BookAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        // Find a reference to the ListView in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);
        final View loadingIndicator = findViewById(R.id.loading_indicator);
        //TextView which will hold empty state
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_list_view);
        //attach empty TextView to the list
        bookListView.setEmptyView(mEmptyStateTextView);
        // Create a new adapter that takes an empty list of books as input
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        // Set the adapter on the ListView
        // so the list can be populated in the user interface
        bookListView.setAdapter(mAdapter);
        //check for internet access
        if (!new CheckNetwork(MainActivity.this).isNetworkAvailable()) {
            mEmptyStateTextView.setText(R.string.no_internet);
        } else {
            mEmptyStateTextView.setText(R.string.search_by_keyword);
        }
        final EditText queryField = (EditText) findViewById(R.id.searchField);
        Button button = (Button) findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = queryField.getText().toString().replace(" ", "+"); //if user iput more than one search term
                String completeUrl = GOOGLE_BOOKS_URL + query;
                //start async task if network is available
                if (new CheckNetwork(MainActivity.this).isNetworkAvailable()) {
                    BookAsyncTask task = new BookAsyncTask();
                    loadingIndicator.setVisibility(View.VISIBLE);
                    task.execute(completeUrl);
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        });
    }

    // method for checking connection from
    // http://stackoverflow.com/questions/23292728/how-to-perform-asynctask-for-checking-internet-connection
    //inner class
    public class CheckNetwork {
        private Context context;

        public CheckNetwork(Context context) {
            this.context = context;
        }

        public boolean isNetworkAvailable() {
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {
        /**
         * This method runs on a background thread and performs the network request.
         * We should not update the UI from a background thread, so we return a list of
         * {@link Book}s as the result.
         */
        @Override
        protected List<Book> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            List<Book> result = QueryUtils.fetchBookData(urls[0]);
            return result;
        }


        @Override
        protected void onPostExecute(List<Book> data) {
            //if there are no books on user's query, update empty text view to notify user
            mEmptyStateTextView.setText(R.string.no_books);
            mAdapter.clear();
            // If there is a valid list of Books, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
