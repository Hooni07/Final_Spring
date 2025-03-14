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
            Optional<Book> productData = bookRepository.findById(id);
            if(productData.isPresent()) {
                return productData;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Book update(Long id, Book book){
        try {
            Optional<Book> productData = bookRepository.findById(id);
            if(productData.isPresent()) {
                Book _product = productData.get();
                _product.setBookName(book.getBookName());
                _product.setPrice(book.getPrice());
                _product.setWriter(book.getWriter());
                bookRepository.save(_product);
                return _product;
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