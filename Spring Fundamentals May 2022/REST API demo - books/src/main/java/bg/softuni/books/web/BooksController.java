package bg.softuni.books.web;

import bg.softuni.books.model.dto.AuthorDTO;
import bg.softuni.books.model.dto.BookDTO;
import bg.softuni.books.model.entity.AuthorEntity;
import bg.softuni.books.service.AuthorService;
import bg.softuni.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/books")
@RestController
public class BooksController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BooksController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping()
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> allBooks = this.bookService.getAllBooks();

        return ResponseEntity.ok(allBooks);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable(name = "id") Long id){
        Optional<BookDTO> bookById = this.bookService.getBookById(id);

        if (bookById.isPresent()) {
            return ResponseEntity.ok(bookById.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<BookDTO> deleteById(@PathVariable(name = "id") Long id){

        this.bookService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<BookDTO> createNewBook(@Valid @RequestBody BookDTO newBook, UriComponentsBuilder uriComponentsBuilder) {

        Long newBookId = this.bookService.createBook(newBook);

        return
                ResponseEntity.
                        created(uriComponentsBuilder.path("/api/books/{id}").
                                build(newBookId)).
                        build();
    }


    @PostMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable("id") Long id,@Valid @RequestBody BookDTO bookDTO) {

        BookDTO updatedBookDTO = this.bookService.persistBook(id, bookDTO);

        if(updatedBookDTO == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedBookDTO);
    }



}
