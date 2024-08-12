package com.subrutin.catalog.service;

import java.util.List;

import com.subrutin.catalog.dto.movie.MovieCreateDTO;
import com.subrutin.catalog.dto.movie.MovieDetailDTO;
import com.subrutin.catalog.dto.movie.MovieUpdateDTO;

public interface MovieService {

  public void insertMovie(MovieCreateDTO moviedto);

  public void updateMovie(int movieid, MovieUpdateDTO movie);

  public boolean deleteMovie(int movieId);

  public List<MovieDetailDTO> getAllMovie();

  public MovieDetailDTO findByIdMovie(int movieId);

}
