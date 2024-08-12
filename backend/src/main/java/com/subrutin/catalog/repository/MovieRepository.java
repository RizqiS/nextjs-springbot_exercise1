package com.subrutin.catalog.repository;

import java.util.List;

import com.subrutin.catalog.model.Movie;

public interface MovieRepository {
  public void insertMovie(Movie movie);

  public void updateMovie(int movieid, Movie movie);

  public boolean deleteMovie(int movieId);

  public List<Movie> getAllMovie();

  public Movie findByIdMovie(int movieId);
}
