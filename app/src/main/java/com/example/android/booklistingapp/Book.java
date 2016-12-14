package com.example.android.booklistingapp;

/**
 * Book class is custom class that has two instance variables, title and authors of the specific book
 */

public class Book {
    private String mTitle;
    private String mAuthors;

    /**
     * constructor for the book class
     *
     * @param title   title of the book
     * @param authors author(s) of the book
     */
    public Book(String title, String authors) {
        mTitle = title;
        mAuthors = authors;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public void setAuthors(String authors) {
        this.mAuthors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "mTitle='" + mTitle + '\'' +
                ", mAuthors='" + mAuthors + '\'' +
                '}';
    }
}

