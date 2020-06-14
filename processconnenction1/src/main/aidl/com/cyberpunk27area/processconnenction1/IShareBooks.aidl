// IShareBooks.aidl
package com.cyberpunk27area.processconnenction1;

// Declare any non-default types here with import statements
import com.cyberpunk27area.processconnenction1.Book;

interface IShareBooks {
   void addBook(in Book book);
   List<Book>getBooks();
}
