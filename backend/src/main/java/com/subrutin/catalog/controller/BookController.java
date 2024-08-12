package com.subrutin.catalog.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subrutin.catalog.dto.book.BookCreateDTO;
import com.subrutin.catalog.dto.book.BookDetailDTO;
import com.subrutin.catalog.dto.book.BookUpdateDTO;
import com.subrutin.catalog.model.CustomResponse;
import com.subrutin.catalog.service.BookService;
import com.subrutin.catalog.util.ValidationError;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/book")
@AllArgsConstructor
@Slf4j
public class BookController {

  private final BookService bookService;

  @GetMapping
  public ResponseEntity<CustomResponse<List<BookDetailDTO>>> getAllBookDetail() {
    log.warn("getAllBookDetail() has been created");
    return ResponseEntity.ok(new CustomResponse<>(200, bookService.findBookListDetail()));
  }

  @PostMapping
  public ResponseEntity<CustomResponse<?>> insertBook(@RequestBody @Valid BookCreateDTO bookDto, Errors errors) {
    log.warn("insertBook(@RequestBody BookCreateDTO book) has been created");

    if (errors.hasErrors()) {

      List<ValidationError> validationsError = errors.getAllErrors().stream()
          .map(error -> {
            String fields = error instanceof FieldError ? ((FieldError) error).getField() : "global";
            return new ValidationError(fields,
                Optional.ofNullable(error).map(e -> e.getDefaultMessage()).orElse("Unknown Error"));
          })
          .collect(Collectors.toList());
      return ResponseEntity.badRequest().body(new CustomResponse<>(HttpStatus.BAD_REQUEST.value(), validationsError));
    }

    bookService.createNewBook(bookDto);
    return ResponseEntity.created(URI.create("/api/book"))
        .body(new CustomResponse<>(HttpStatus.CREATED.value(), "book has been created"));
  }

  @GetMapping("/{bookid}")
  public ResponseEntity<CustomResponse<?>> findBookId(@PathVariable("bookid") Long bookid) {

    try {
      // check if book is null or not
      if (bookService.findBookDetailById(bookid) == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new CustomResponse<>(HttpStatus.NOT_FOUND.value(), "Book with ID " + bookid + " not found."));
      }

      var findBook = bookService.findBookDetailById(bookid);

      return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(), findBook));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new CustomResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred"));
    }

  }

  @PatchMapping("/{bookid}")
  public ResponseEntity<CustomResponse<?>> updateBook(@PathVariable("bookid") Long bookid,
      @RequestBody @Valid BookUpdateDTO bookDto,
      Errors errors) {

    if (errors.hasErrors()) {
      List<ValidationError> validationError = errors.getAllErrors().stream()
          .map(error -> {
            String fields = error instanceof FieldError ? ((FieldError) error).getField() : "global";
            return new ValidationError(fields,
                Optional.ofNullable(error).map(err -> err.getDefaultMessage()).orElse("Unknown Error"));
          }).collect(Collectors.toList());

      return ResponseEntity.badRequest().body(new CustomResponse<>(HttpStatus.BAD_REQUEST.value(), validationError));
    }

    try {
      // check if book is null or not
      if (bookService.findBookDetailById(bookid) == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new CustomResponse<>(HttpStatus.NOT_FOUND.value(), "Book with ID " + bookid + " not found."));
      }

      // update if book not null
      bookService.updateBook(bookid, bookDto);
      return ResponseEntity.created(URI.create("/api/book/" + bookid))
          .body(new CustomResponse<>(HttpStatus.CREATED.value(), "book has been updated"));
    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new CustomResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred"));
    }
  }

  @DeleteMapping("/{bookid}")
  public ResponseEntity<CustomResponse<String>> deleteBook(@PathVariable("bookid") Long bookid) {
    try {
      var isBookDeleted = bookService.deleteBook(bookid);

      if (!isBookDeleted) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new CustomResponse<>(HttpStatus.NOT_FOUND.value(), "Book with ID " + bookid + " not found."));
      }

      return ResponseEntity.status(HttpStatus.OK)
          .body(new CustomResponse<>(HttpStatus.OK.value(), "Book with ID " + bookid + " has been delete"));

    } catch (Exception ex) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new CustomResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred"));
    }

  }

}
