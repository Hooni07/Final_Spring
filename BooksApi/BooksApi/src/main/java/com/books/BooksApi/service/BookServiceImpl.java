package com.books.BooksApi.service;

import com.books.BooksApi.domain.Book;
import com.books.BooksApi.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Book save(Book book){

        try {
            return bookRepository.save(
                    new Book(
                            book.getBookName(),
                            book.getPrice(),
                            book.getWriter()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Book> findById(Long id){
        try {
            Optional<Book> bookData = bookRepository.findById(id);
            if(bookData.isPresent()) {
                return bookData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book update(Long id, Book book){
        try {
            Optional<Book> bookData = bookRepository.findById(id);
            if(bookData.isPresent()) {
                Book _book = bookData.get();
                _book.setBookName(book.getBookName());
                _book.setPrice(book.getPrice());
                _book.setWriter(book.getWriter());
                bookRepository.save(_book);
                return _book;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long id){
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}