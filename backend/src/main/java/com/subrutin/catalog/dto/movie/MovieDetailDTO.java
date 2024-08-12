package com.subrutin.catalog.dto.movie;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.subrutin.catalog.model.MovieStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDetailDTO {

  private int movieId;

  private String movieTitle;

  private String movieDescription;

  private MovieStatus status;

  private Double movieRate;
}
