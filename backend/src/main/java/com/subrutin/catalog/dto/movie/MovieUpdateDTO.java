package com.subrutin.catalog.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.subrutin.catalog.model.MovieStatus;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MovieUpdateDTO {
  @JsonProperty(value = "title")
  @NotBlank(message = "title movie is required")
  private String movieTitle;

  @NotBlank(message = "description movie is required")
  @Size(min = 6, message = "Description movie must be at least 6 characters long")
  private String movieDescription;

  @NotNull(message = "movie status is required")
  private MovieStatus movieStatus;

  @NotNull
  private Boolean isAdultMovie;

  @NotNull
  @DecimalMin(value = "1.0", message = "Movie rate must be greater than 0.0")
  @DecimalMax(value = "10.0", message = "Movie rate must be less than or equal to 10.0")
  private Double movieRate;
}
