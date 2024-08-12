package com.subrutin.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
  private int movieId;
  private String movieTitle;
  private String movieDescription;
  private Double movieRate;
  private boolean isAdultMovie;
  private MovieStatus status;
}
