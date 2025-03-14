package com.books.BooksApi.controller;

import com.books.BooksApi.domain.Book;
import com.books.BooksApi.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class BookController {
    @Autowired
    BookServiceImpl bookService;

    @GetMapping("/books/{id}")
    public ResponseEntity<Optional<Book>> getBookById(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(bookService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        try {
            ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(bookService.save(book));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable("id") long id,
            @RequestBody Book book
    ) {
        try {
            ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(bookService.update(id, book));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable("id") long id) {
        try {
            bookService.delete(id);
            ResponseEntity.noContent();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}