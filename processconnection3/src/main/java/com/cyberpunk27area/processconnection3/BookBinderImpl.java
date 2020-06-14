package com.cyberpunk27area.processconnection3;

import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookBinderImpl extends IBookBinder.Stub {

    private CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<>();

    @Override
    public void addBook(Book book) throws RemoteException {

        books.add(book);

    }

    @Override
    public List<Book> getBooks() throws RemoteException {
        return books;
    }
}
