package com.subrutin.catalog.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.subrutin.catalog.model.Movie;
import com.subrutin.catalog.model.MovieStatus;
import com.subrutin.catalog.repository.MovieRepository;
import com.subrutin.catalog.repository.impl.MovieRepositoryImpl;

@Configuration
@ComponentScan(basePackages = { "com.subrutin.catalog" })
public class MovieConfig {

  private final Logger log = LoggerFactory.getLogger(getClass());

  public MovieConfig() {
    log.warn("MovieConfig has been created");
  }

  @Bean
  public Movie movie1() {
    return new Movie(1, "Movie 1", "Movie 1 Description", 5.0, false, MovieStatus.ONGOING);
  }

  @Bean
  public Movie movie2() {
    return new Movie(2, "Movie 2", "Movie 2 Description", 8.0, false, MovieStatus.COMPLETED);
  }

  @Bean(name = { "movieRepository" })
  public MovieRepository movieRepository(@Qualifier("movie1") Movie movie1, @Qualifier("movie2") Movie movie2) {

    List<Movie> listMovie = new ArrayList<>();
    listMovie.add(movie1);
    listMovie.add(movie2);

    MovieRepositoryImpl movieRepositoryImpl = new MovieRepositoryImpl(listMovie);

    return movieRepositoryImpl;
  }
}
