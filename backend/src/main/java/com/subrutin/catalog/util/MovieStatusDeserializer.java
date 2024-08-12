package com.subrutin.catalog.util;

import com.subrutin.catalog.model.MovieStatus;

public class MovieStatusDeserializer extends EnumDeserializer<MovieStatus> {
  public MovieStatusDeserializer() {
    super(MovieStatus.class);
  }
}
