# 백엔드(Java)온보딩트랙 파이널 프로젝트

## 코드

### 0. 초기 세팅(application.properties)
![image](https://github.com/user-attachments/assets/0fd4f104-71f6-4750-8bd4-7a51a23cab55)

### 1. domain 설정(domain-Books class)
```java
@Entity
@Table(name="books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "price")
    private int price;

    @Column(name = "writer")
    private String writer;


    // 생성자 생성
    public Book(String bookName, int price, String writer) {
        this.bookName = bookName;
        this.price = price;
        this.writer = writer;
    }

    // getter 및 setter(생략)
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

```

### 2. repository 설정(repository-BookRepository)
```java
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
```

### 3. service 설정(service)
- BookService 설정
```java
public interface BookService {

    public Book save(Book book);
    public Optional<Book> findById(Long id);
    public Book update(Long id, Book book);
    public void delete(Long id);
}
```
- BookServiceImpl 설정
    - save
      ```java
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
      ```
    - findById
      ```java
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
      ```
    - update
      ```java
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
      ```
    - delete
      ```java
      @Override
      public void delete(Long id){
        try {
            bookRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
      }
      ```

### 4. Controller 설정(controller-BookController)
```java
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
```

## 구현(API Test)
### GET_POST
![GET_POST](https://github.com/user-attachments/assets/cd55dae9-f545-4706-a2fe-1b063e988c3b)

### PUT_DELETE
![PUT_DELETE](https://github.com/user-attachments/assets/8110ba34-7820-4cf0-8cd7-42849473d915)

