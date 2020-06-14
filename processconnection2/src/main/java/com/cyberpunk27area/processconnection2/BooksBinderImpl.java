package com.cyberpunk27area.processconnection2;

import android.os.RemoteException;
import android.util.Log;

import com.cyberpunk27area.processconnenction1.Book;
import com.cyberpunk27area.processconnenction1.IBooksBinder;

import java.util.ArrayList;
import java.util.List;

public class BooksBinderImpl extends IBooksBinder.Stub {

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
