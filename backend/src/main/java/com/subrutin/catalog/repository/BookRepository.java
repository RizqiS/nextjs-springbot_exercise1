package com.subrutin.catalog.repository;

import java.util.List;
import java.util.Optional;

import com.subrutin.catalog.model.Book;

public interface BookRepository {
  public Optional<Book> findBookById(Long id);

  public List<Book> findAll();

  public void saveBook(Book book);

  public void updateBook(Long id, Book book);

  public boolean deleteBook(Long id);
}
