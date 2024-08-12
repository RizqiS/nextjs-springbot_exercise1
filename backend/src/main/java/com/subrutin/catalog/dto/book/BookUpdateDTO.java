package com.subrutin.catalog.dto.book;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookUpdateDTO {

  @NotBlank(message = "title is required from spring java")
  private String bookTitle;

  @NotBlank(message = "description is required from spring java")
  @Size(min = 6, message = "Description book must be at least 6 characters long")
  private String bookDescription;

}
