package com.moveflix.MovieAPI.service;

import com.moveflix.MovieAPI.dto.MovieDto;
import com.moveflix.MovieAPI.dto.MoviePageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



public interface MovieService {

    MovieDto addMovie(MovieDto movieDto, MultipartFile multipartFile) throws IOException;

    MovieDto getMovieById(int movieId);

    List<MovieDto> getAllMovies();

    MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize);

    MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir);

    MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile multipartFile) throws IOException;

    String deleteMovie(Integer movieId) throws IOException;
}
