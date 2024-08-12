package com.subrutin.catalog.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.subrutin.catalog.dto.movie.MovieCreateDTO;
import com.subrutin.catalog.dto.movie.MovieDetailDTO;
import com.subrutin.catalog.dto.movie.MovieUpdateDTO;
import com.subrutin.catalog.model.CustomResponse;
import com.subrutin.catalog.service.MovieService;
import com.subrutin.catalog.util.ValidationError;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/movies")
@AllArgsConstructor
@Slf4j
public class MovieController {

  private final MovieService movieService;

  @GetMapping
  public ResponseEntity<CustomResponse<List<MovieDetailDTO>>> getAllMovie() {
    log.warn("getAllMovie() has been created");
    return ResponseEntity.ok(new CustomResponse<>(200, movieService.getAllMovie()));
  }

  @GetMapping("/{movieid}")
  public ResponseEntity<CustomResponse<?>> findMovieById(@PathVariable("movieid") int movieId) {

    var movieDto = movieService.findByIdMovie(movieId);
    if (movieDto == null) {
      var errorResponse = new CustomResponse<>(HttpStatus.NOT_FOUND.value(), "movie with " + movieId + " not found.");
      return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }

    return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(), movieDto));
  }

  @PostMapping
  public ResponseEntity<CustomResponse<?>> saveMovie(@RequestBody @Valid MovieCreateDTO moviedto, Errors errors) {

    if (errors.hasErrors()) {
      List<ValidationError> validationErrors = errors.getAllErrors().stream()
          .map(error -> {
            String fields = error instanceof FieldError ? ((FieldError) error).getField() : "global";
            return new ValidationError(fields,
                Optional.ofNullable(error).map(e -> e.getDefaultMessage()).orElse("unknown error"));
          })
          .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(new CustomResponse<>(HttpStatus.BAD_REQUEST.value(),
          validationErrors));
    }

    movieService.insertMovie(moviedto);

    return ResponseEntity.created(URI.create("/api/movies"))
        .body(new CustomResponse<>(HttpStatus.CREATED.value(), "movie has been save"));
  }

  @PatchMapping("/{movieid}")
  public ResponseEntity<CustomResponse<?>> updateMovie(@PathVariable("movieid") int movieid,
      @RequestBody @Valid MovieUpdateDTO moviedto,
      Errors errors) {

    if (errors.hasErrors()) {
      List<ValidationError> validationErrors = errors.getAllErrors().stream()
          .map(error -> {
            String fields = error instanceof FieldError ? ((FieldError) error).getField() : "global";
            return new ValidationError(fields,
                Optional.ofNullable(error).map(err -> err.getDefaultMessage()).orElse("unknown error"));
          }).collect(Collectors.toList());
      return ResponseEntity.badRequest().body(new CustomResponse<>(HttpStatus.BAD_REQUEST.value(), validationErrors));
    }

    movieService.updateMovie(movieid, moviedto);
    return ResponseEntity.ok(new CustomResponse<>(HttpStatus.OK.value(), "movie has been updated"));
  }

  @DeleteMapping("/{movieid}")
  public ResponseEntity<CustomResponse<String>> deleteMovie(@PathVariable("movieid") int movieId) {
    var isMovie = movieService.deleteMovie(movieId);
    if (!isMovie) {
      CustomResponse<String> errorResponse = new CustomResponse<>(HttpStatus.NOT_FOUND.value(),
          "Movie with ID " + movieId + " not found.");
      return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }

    return ResponseEntity.ok(
        new CustomResponse<>(HttpStatus.OK.value(), "Movie with ID " + movieId + " has been deleted successfully."));
  }

}
