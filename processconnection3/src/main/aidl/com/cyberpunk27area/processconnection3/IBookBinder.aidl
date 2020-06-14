// IBookBinder.aidl
package com.cyberpunk27area.processconnection3;

// Declare any non-default types here with import statements

import com.cyberpunk27area.processconnection3.Book;

interface IBookBinder {
    void addBook(in Book book);
    List<Book> getBooks();
}
