package bg.softuni.books.model.dto;

import javax.validation.constraints.NotNull;

public class BookDTO {

  private Long id;

  @NotNull
  private String title;
  private String isbn;

  @NotNull
  private AuthorDTO author;

  public Long getId() {
    return id;
  }

  public BookDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public BookDTO setTitle(String title) {
    this.title = title;
    return this;
  }

  public String getIsbn() {
    return isbn;
  }

  public BookDTO setIsbn(String isbn) {
    this.isbn = isbn;
    return this;
  }

  public AuthorDTO getAuthor() {
    return author;
  }

  public BookDTO setAuthor(AuthorDTO author) {
    this.author = author;
    return this;
  }
}
