package wolox.training.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import wolox.training.exceptions.BookIdMismatchException;
import wolox.training.exceptions.BookNotFoundException;
import wolox.training.models.Book;
import wolox.training.repositories.BookRepository;
import wolox.training.services.OpenLibraryService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private OpenLibraryService openLibraryService;

    @GetMapping
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public Book findId(@PathVariable(required = true) Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No se encontro el libro "));
    }

    @GetMapping
    @RequestMapping(params = "author")
    public Book findByAuthor(@RequestParam(required = true) String author) {
        return bookRepository.findFirstByAuthorOrderByYearDesc(author)
                .orElseThrow(() -> new BookNotFoundException("No se encontro el ultimo libro del autor "));
    }

    @GetMapping("/search")
    public Iterable<Book> findByAllParameters(@RequestParam(required = false) String id,
            @RequestParam(required = false) String genre, @RequestParam(required = false) String author,
            @RequestParam(required = false) String image, @RequestParam(required = false) String title,
            @RequestParam(required = false) String subtitle, @RequestParam(required = false) String publisher,
            @RequestParam(required = false) String yearFrom, @RequestParam(required = false) String yearTo,
            @RequestParam(required = false) String pages, @RequestParam(required = false) String isbn) {
        return bookRepository.findByAllParameters(id, genre, author, image, title, subtitle, publisher, yearFrom,
                yearTo, pages, isbn);
    }

    @GetMapping
    @RequestMapping(params = "isbn")
    public ResponseEntity<Book> findByIsbn(@RequestParam(required = true) String isbn) throws Exception {
        Optional<Book> OBook = bookRepository.findFirstByIsbn(isbn);
        if (OBook.isPresent()) {

            return new ResponseEntity<Book>(OBook.get(), HttpStatus.OK);

        } else {

            Book book = openLibraryService.bookInfo(isbn).orElseThrow(
                    () -> new BookNotFoundException("No se encontro el libro en la API, ni en la base interna"));

            return new ResponseEntity<Book>(bookRepository.save(book), HttpStatus.CREATED);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable(required = true) Long id) {
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No existe el libro de id ingresado"));
        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(@RequestBody Book book, @PathVariable Long id) {
        if (!id.equals(book.getId())) {
            throw new BookIdMismatchException("Id invalido");
        }
        bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No existe el libro de id ingresado"));
        return bookRepository.save(book);
    }

}
