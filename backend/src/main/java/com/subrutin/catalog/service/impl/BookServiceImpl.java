package com.subrutin.catalog.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.dto.book.BookCreateDTO;
import com.subrutin.catalog.dto.book.BookDetailDTO;
import com.subrutin.catalog.dto.book.BookUpdateDTO;
import com.subrutin.catalog.model.Author;
import com.subrutin.catalog.model.Book;
import com.subrutin.catalog.repository.BookRepository;
import com.subrutin.catalog.service.BookService;

import lombok.AllArgsConstructor;

@Service("bookService")
@AllArgsConstructor
public class BookServiceImpl implements BookService {

  private final BookRepository bookRepository;

  @Override
  public BookDetailDTO findBookDetailById(Long bookId) {
    // Find the book by ID
    Optional<Book> bookOptional = bookRepository.findBookById(bookId);

    // Check if the book is present
    if (bookOptional.isEmpty()) {
      return null;
    }

    // Map the Book to BookDetailDTO
    Book book = bookOptional.get();
    BookDetailDTO bookDto = new BookDetailDTO();
    bookDto.setBookId(book.getId());
    bookDto.setAuthorName(book.getAuthor().getName());
    bookDto.setBookTitle(book.getTitle());
    bookDto.setBookDescription(book.getDescription());

    return bookDto;
  }

  @Override
  public List<BookDetailDTO> findBookListDetail() {
    List<Book> bookList = bookRepository.findAll();

    var data = bookList.stream().map(b -> {
      BookDetailDTO bookDto = new BookDetailDTO();
      bookDto.setBookId(b.getId());
      bookDto.setAuthorName(b.getAuthor().getName());
      bookDto.setBookTitle(b.getTitle());
      bookDto.setBookDescription(b.getDescription());
      return bookDto;
    }).collect(Collectors.toList());
    return data;
  }

  @Override
  public void createNewBook(BookCreateDTO bookDto) {
    Author author = new Author();
    author.setName(bookDto.getAuthorName());

    Book book = new Book();
    book.setAuthor(author);
    book.setTitle(bookDto.getBookTitle());
    book.setDescription(bookDto.getBookDescription());

    bookRepository.saveBook(book);
  }

  @Override
  public void updateBook(Long id, BookUpdateDTO bookDto) {
    Book book = new Book();
    book.setTitle(bookDto.getBookTitle());
    book.setDescription(bookDto.getBookDescription());

    bookRepository.updateBook(id, book);
  }

  @Override
  public boolean deleteBook(Long id) {
    return bookRepository.deleteBook(id);
  }

}
