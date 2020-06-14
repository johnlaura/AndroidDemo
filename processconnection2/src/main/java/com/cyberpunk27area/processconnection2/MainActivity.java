package com.cyberpunk27area.processconnection2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.cyberpunk27area.processconnenction1.Book;
import com.cyberpunk27area.processconnenction1.IBitmapBinder;
import com.cyberpunk27area.processconnenction1.IBooksBinder;
import com.cyberpunk27area.processconnenction1.IShareBooks;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private IBinder bookBinder;
    private IBinder bitmapBinder;
    private IBooksBinder booksBinderImpl;
    private IBitmapBinder bitmapBinderImpl;
    private ImageView mIvBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIvBitmap = findViewById(R.id.iv_bitmap);

        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        addBooks();
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        showBooks();
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_setbitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setBitmap();
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_showbitmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        showBitmap();
                    }
                }).start();
            }
        });

        findViewById(R.id.btn_connection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connection();
            }
        });

        findViewById(R.id.btn_shared_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSharedBook();
            }
        });

        findViewById(R.id.btn_getShared_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedBooks();
            }
        });
    }

    private IShareBooks shareBooks;

    private void connection() {
        Intent intent = new Intent();
        intent.setAction("com.cyberpunk27area.processconnenction1.SharedBookService");
        intent.setPackage("com.cyberpunk27area.processconnenction1");//设置服务端的package
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e("abc", "connection");
                shareBooks = IShareBooks.Stub.asInterface(service);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }

        }, Context.BIND_AUTO_CREATE);
    }

    private void addSharedBook() {
        Log.e("abc", "addSharedBook");
        for (int i = 0; i < 10; i++) {
            try {
                shareBooks.addBook(new Book("client shared title:" + i, "server shared content:" + i));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void getSharedBooks() {
        try {
            List<Book> books = shareBooks.getBooks();
            for (Book book : books) {
                Log.e("abc", "books title:" + book.getTitle());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void addBooks() {
        try {
            BinderPool binderPool = BinderPool.getInstance(MainActivity.this);
            bookBinder = binderPool.queryBinder(BinderPool.BINDER_BOOKS);
            booksBinderImpl = BooksBinderImpl.asInterface(bookBinder);
            for (int i = 0; i < 10; i++) {
                booksBinderImpl.addBook(new Book("client title:" + i, "client content:" + i));
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void showBooks() {
        BinderPool binderPool = BinderPool.getInstance(MainActivity.this);
        bookBinder = binderPool.queryBinder(BinderPool.BINDER_BOOKS);
        booksBinderImpl = BooksBinderImpl.asInterface(bookBinder);
        try {
            List<Book> books = booksBinderImpl.getBooks();
            for (Book book : books) {
                Log.e("abc", "books title:" + book.getTitle());
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void setBitmap() {
        BinderPool binderPool = BinderPool.getInstance(MainActivity.this);
        bitmapBinder = binderPool.queryBinder(BinderPool.BINDER_BITMAP);
        bitmapBinderImpl = BitmapBinderImpl.asInterface(bitmapBinder);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.vincent, null);
        try {
            bitmapBinderImpl.setBitmap(bitmap);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void showBitmap() {
        BinderPool binderPool = BinderPool.getInstance(MainActivity.this);
        bitmapBinder = binderPool.queryBinder(BinderPool.BINDER_BITMAP);
        bitmapBinderImpl = BitmapBinderImpl.asInterface(bitmapBinder);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    mIvBitmap.setImageBitmap(bitmapBinderImpl.getBitmap());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
