package com.books.BooksApi.service;

import com.books.BooksApi.domain.Book;

import java.util.Optional;

public interface BookService {

    public Book save(Book book);
    public Optional<Book> findById(Long id);
    public Book update(Long id, Book book);
    public void delete(Long id);
}