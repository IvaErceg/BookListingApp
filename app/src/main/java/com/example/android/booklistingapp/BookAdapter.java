package com.example.android.booklistingapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Iva on 13.12.2016..
 */

public class BookAdapter extends ArrayAdapter<Book> {
    public BookAdapter(Activity context, ArrayList<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Book currentBook = getItem(position);
        String title = currentBook.getTitle();
        TextView titleView = (TextView) listItemView.findViewById(R.id.title);
        titleView.setText(title);
        String authors = currentBook.getAuthors();
        TextView authorsView = (TextView) listItemView.findViewById(R.id.author);
        authorsView.setText(authors);

        return listItemView;
    }
}
