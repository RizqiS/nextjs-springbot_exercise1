package com.subrutin.catalog.config;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.subrutin.catalog.model.Author;
import com.subrutin.catalog.model.Book;
import com.subrutin.catalog.repository.BookRepository;
import com.subrutin.catalog.repository.impl.BookRepositoryImpl;

@ComponentScan(basePackages = { "com.subrutin.catalog" })
@Configuration
public class AppConfig {

  private final Logger log = LoggerFactory.getLogger(getClass());

  public AppConfig() {
    log.warn("AppConfig() has been created");
  }

  @Bean
  public Author author() {
    return new Author(1L, "Rizqi Nurul Sentosa", -16401L);
  }

  @Bean
  public Book book1(Author author) {
    Book book = new Book();
    book.setId(1L);
    book.setTitle("Book 1");
    book.setDescription("Book 1 Description");
    book.setAuthor(author);
    return book;
  }

  @Bean
  public Book book2(Author author) {
    Book book = new Book();
    book.setId(2L);
    book.setTitle("Book 2");
    book.setDescription("Book 2 Description");
    book.setAuthor(author);
    return book;
  }

  @Bean(name = { "bookRepository" })
  public BookRepository bookRepository(@Qualifier("book1") Book book1, @Qualifier("book2") Book book2) {
    log.info("bookRepository(@Qualifier(\"book1\") Book book1, @Qualifier(\"book2\") Book book2) has been created");

    Map<Long, Book> bookMap = new HashMap<>();
    bookMap.put(1L, book1);
    bookMap.put(2L, book2);

    BookRepositoryImpl bookRepositoryImpl = new BookRepositoryImpl();
    bookRepositoryImpl.setBookMap(bookMap);
    return bookRepositoryImpl;
  }
}
