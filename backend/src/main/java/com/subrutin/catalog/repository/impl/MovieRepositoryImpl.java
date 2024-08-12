package com.subrutin.catalog.repository.impl;

import java.util.List;

import com.subrutin.catalog.model.Movie;
import com.subrutin.catalog.repository.MovieRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class MovieRepositoryImpl implements MovieRepository {

  private final List<Movie> listMovie;

  @Override
  public boolean deleteMovie(int movieId) {

    if (listMovie.size() < movieId) {
      return false;
    }

    var movie = listMovie.get(movieId - 1);
    if (movie == null) {
      return false;
    }

    return listMovie.remove(movie);
  }

  @Override
  public Movie findByIdMovie(int movieId) {
    var movie = listMovie.get(movieId - 1);

    if (movie == null)
      return null;

    return movie;
  }

  @Override
  public List<Movie> getAllMovie() {
    return listMovie;
  }

  @Override
  public void insertMovie(Movie movie) {
    if (movie == null) {
      return;
    }

    int size = listMovie.size();
    movie.setMovieId(size + 1);

    listMovie.add(movie);
  }

  @Override
  public void updateMovie(int movieid, Movie movie) {
    if (movie == null) {
      return;
    }

    listMovie.set(movieid - 1, movie);
  }

}