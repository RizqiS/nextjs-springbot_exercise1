package com.subrutin.catalog.dto.book;

import java.io.Serializable;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
public class BookDetailDTO implements Serializable {
  private Long bookId;
  private String authorName;
  private String bookTitle;
  private String bookDescription;
}
