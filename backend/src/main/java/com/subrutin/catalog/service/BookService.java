package com.subrutin.catalog.service;

import java.util.List;

import com.subrutin.catalog.dto.book.BookCreateDTO;
import com.subrutin.catalog.dto.book.BookDetailDTO;
import com.subrutin.catalog.dto.book.BookUpdateDTO;

public interface BookService {
  public BookDetailDTO findBookDetailById(Long bookId);

  public List<BookDetailDTO> findBookListDetail();

  public void createNewBook(BookCreateDTO bookDto);

  public void updateBook(Long id, BookUpdateDTO bookDto);

  public boolean deleteBook(Long id);
}
