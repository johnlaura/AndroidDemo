package com.cyberpunk27area.processconnenction1;

import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BooksBinderImpl extends IBooksBinder.Stub {

    private BooksBinderImpl() {

    }

    private static BooksBinderImpl INSTANCE = null;

    public static BooksBinderImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (BooksBinderImpl.class) {
                INSTANCE = new BooksBinderImpl();
            }
        }
        return INSTANCE;
    }

    private ArrayList<Book> books = new ArrayList<Book>();

    @Override
    public void addBook(Book book) throws RemoteException {
        Log.e("abc", "title:" + book.getTitle());
        books.add(book);
    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        return books;
    }
}
