package com.subrutin.catalog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.subrutin.catalog.dto.movie.MovieCreateDTO;
import com.subrutin.catalog.dto.movie.MovieDetailDTO;
import com.subrutin.catalog.dto.movie.MovieUpdateDTO;
import com.subrutin.catalog.model.Movie;
import com.subrutin.catalog.repository.MovieRepository;
import com.subrutin.catalog.service.MovieService;

import lombok.AllArgsConstructor;

@Service("movieService")
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

  private final MovieRepository movieRepository;

  @Override
  public boolean deleteMovie(int movieId) {
    return movieRepository.deleteMovie(movieId);
  }

  @Override
  public MovieDetailDTO findByIdMovie(int movieId) {
    var movie = movieRepository.findByIdMovie(movieId);

    if (movie == null) {
      return null;
    }

    var moviedto = new MovieDetailDTO(movie.getMovieId(), movie.getMovieTitle(), movie.getMovieDescription(),
        movie.getStatus(), movie.getMovieRate());

    return moviedto;
  }

  @Override
  public List<MovieDetailDTO> getAllMovie() {

    List<MovieDetailDTO> movieDetailDto = movieRepository.getAllMovie()
        .stream().map(movie -> {
          var moviedto = new MovieDetailDTO(movie.getMovieId(), movie.getMovieTitle(), movie.getMovieDescription(),
              movie.getStatus(),
              movie.getMovieRate());
          return moviedto;
        }).collect(Collectors.toList());

    return movieDetailDto;
  }

  @Override
  public void insertMovie(MovieCreateDTO moviedto) {
    var movie = new Movie();
    movie.setMovieTitle(moviedto.getMovieTitle());
    movie.setMovieDescription(moviedto.getMovieDescription());
    movie.setMovieRate(moviedto.getMovieRate());
    movie.setAdultMovie(moviedto.getIsAdultMovie());
    movie.setStatus(moviedto.getMovieStatus());
    movieRepository.insertMovie(movie);
  }

  @Override
  public void updateMovie(int movieid, MovieUpdateDTO movie) {
    var movies = movieRepository.findByIdMovie(movieid);

    movies.setMovieTitle(movie.getMovieTitle());
    movies.setMovieDescription(movie.getMovieDescription());
    movies.setStatus(movie.getMovieStatus());
    movies.setMovieRate(movie.getMovieRate());
    movies.setAdultMovie(movie.getIsAdultMovie());

    movieRepository.updateMovie(movies.getMovieId(), movies);
  }

}
