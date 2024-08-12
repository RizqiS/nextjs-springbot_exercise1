package com.subrutin.catalog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.subrutin.catalog.dto.book.BookDetailDTO;
import com.subrutin.catalog.service.BookService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class BookResource {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final BookService bookService;

  @GetMapping("api/test/book/{id}")
  public BookDetailDTO findBookByIdDTO(@PathVariable("id") Long bookId) {
    log.info("findBookById() has been created");

    var book = bookService.findBookDetailById(bookId);
    return book;
  }

}
