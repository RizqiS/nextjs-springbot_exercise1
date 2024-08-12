package com.subrutin.catalog.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.subrutin.catalog.model.Book;
import com.subrutin.catalog.repository.BookRepository;

import lombok.Data;

@Data
@Repository
public class BookRepositoryImpl implements BookRepository {
  private Map<Long, Book> bookMap;

  @Override
  public Optional<Book> findBookById(Long id) {
    return Optional.ofNullable(bookMap.get(id));
  }

  @Override
  public List<Book> findAll() {
    List<Book> bookList = new ArrayList<>(bookMap.values());
    return bookList;
  }

  @Override
  public void saveBook(Book book) {
    long size = (long) bookMap.size();
    book.setId(size + 1);

    bookMap.put(book.getId(), book);
  }

  @Override
  public void updateBook(Long id, Book book) {

    Optional<Book> existingBookOptional = findBookById(id);

    if (existingBookOptional.isEmpty()) {
      return;
    }

    Book existingBook = existingBookOptional.get();
    existingBook.setTitle(book.getTitle());
    existingBook.setDescription(book.getDescription());

    bookMap.put(existingBook.getId(), existingBook);
  }

  @Override
  public boolean deleteBook(Long id) {
    Optional<Book> existingBookOptional = findBookById(id);

    if (existingBookOptional.isEmpty()) {
      return false;
    }

    bookMap.remove(id);

    return true;
  }

}
