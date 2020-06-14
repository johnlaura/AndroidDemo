package com.cyberpunk27area.processconnenction1;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SharedBooksService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    private CopyOnWriteArrayList<Book> books = new CopyOnWriteArrayList<>();

    private IShareBooks.Stub stub = new IShareBooks.Stub() {

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {

            int check = checkCallingOrSelfPermission("com.cyberpunk27area.processconnenction1.SHARED_BOOKS");

            if (check == PackageManager.PERMISSION_DENIED) {
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            books.add(book);
        }

        @Override
        public List<Book> getBooks() throws RemoteException {
            return books;
        }
    };
}
