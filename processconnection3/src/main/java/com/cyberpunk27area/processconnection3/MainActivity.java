package com.cyberpunk27area.processconnection3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnAddBooks).setOnClickListener(new View.OnClickListener() {
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

        findViewById(R.id.btnShowBooks).setOnClickListener(new View.OnClickListener() {
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
    }

    private void addBooks() {
        BookBinder mBookBinder = BookBinder.getInstance(MainActivity.this);
        for (int i = 0; i < 10; i++) {
            mBookBinder.addBook(new Book("title:" + i, "content:" + i));
        }
    }

    private void showBooks() {
        BookBinder mBookBinder = BookBinder.getInstance(MainActivity.this);
        List<Book> books = mBookBinder.getBooks();
        for (Book book : books) {
            Log.e("abc", "" + book.getTitle());
        }
    }
}
