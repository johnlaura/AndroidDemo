package com.cyberpunk27area.processconnection3;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CountDownLatch;

public class BookBinder {

    volatile private static BookBinder INSTANCE = null;

    private IBookBinder mBookBinder;
    private Context mContext;
    private CountDownLatch mCountDownLatch = new CountDownLatch(1);
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookBinder = IBookBinder.Stub.asInterface(service);
            try {
                mBookBinder.asBinder().linkToDeath(mDeathRecipient, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mCountDownLatch.countDown();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            //flag 0 是什么
            mBookBinder.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBookBinder = null;
            connectionBinderService(mContext);
        }
    };

    public static BookBinder getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BookBinder.class) {
                INSTANCE = new BookBinder(context);
            }
        }
        return INSTANCE;
    }

    private BookBinder(Context context) {
        mContext = context.getApplicationContext();
        connectionBinderService(mContext);
    }

    private synchronized void connectionBinderService(Context context) {
        Intent service = new Intent(context, BookBinderService.class);
        context.bindService(service, mServiceConnection, Context.BIND_AUTO_CREATE);
        try {
            mCountDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addBook(Book book) {
        try {
            mBookBinder.addBook(book);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public synchronized List<Book> getBooks() {
        try {
            return mBookBinder.getBooks();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

}
