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

    @Column(name = "writer")
    private String writer;

    @Column(name = "price")
    private int price;

    // 생성자 생성
    public Book(String productName, String writer, int price) {
        this.bookName = productName;
        this.writer = writer;
        this.price = price;
    }

    // getter 및 setter(생략)
    // ...
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
            Optional<Book> productData = bookRepository.findById(id);
            if(productData.isPresent()) {
                return productData;
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
    public ResponseEntity<Optional<Book>> getProductById(@PathVariable("id") long id) {
        try {
            return ResponseEntity.ok(bookService.findById(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createProduct(@RequestBody Book book) {
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
    public ResponseEntity<Book> deleteProduct(@PathVariable("id") long id) {
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
