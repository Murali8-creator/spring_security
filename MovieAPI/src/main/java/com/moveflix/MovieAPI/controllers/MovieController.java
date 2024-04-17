package com.moveflix.MovieAPI.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moveflix.MovieAPI.dto.MovieDto;
import com.moveflix.MovieAPI.exceptions.EmptyFileException;
import com.moveflix.MovieAPI.service.MovieService;
import com.moveflix.MovieAPI.utils.AppConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    private final Logger logger = LoggerFactory.getLogger(MovieController.class);

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/add-movie")
    public ResponseEntity<MovieDto> addMovieHandler(
            @RequestPart MultipartFile multipartFile,
            @RequestPart String movieDto) throws IOException {

        if(multipartFile.isEmpty()){
            throw new EmptyFileException("File is empty! Please upload a file.");
        }

        MovieDto dto = convertToMovieDto(movieDto);
        logger.info("post-dto : "+ dto);
        return new ResponseEntity<>(movieService.addMovie(dto, multipartFile), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieByIdHandler(@PathVariable Integer movieId) {
        return new ResponseEntity<>(movieService.getMovieById(movieId), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MovieDto>> getAllMoviesHandler() {
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @PutMapping("/update/{movieId}")
    public ResponseEntity<MovieDto> updateMovieHandler(
            @PathVariable Integer movieId,
            @RequestPart MultipartFile multipartFile,
            @RequestPart String movieDto) throws IOException {

        logger.info("movieId : "+ movieId);
        logger.info("multipartFile : "+ multipartFile);
        logger.info("movieDto : "+ movieDto);
        if (multipartFile.isEmpty())multipartFile = null;


        MovieDto dto = convertToMovieDto(movieDto);
        logger.info("update-dto : "+ dto);
        return new ResponseEntity<>(movieService.updateMovie(movieId, dto, multipartFile), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return new ResponseEntity<>(movieService.deleteMovie(movieId), HttpStatus.OK);
    }

    @GetMapping("/allMoviesPage")
    public ResponseEntity<?> getAllMoviesWithPaginationHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize) {

        return new ResponseEntity<>(movieService.getAllMoviesWithPagination(pageNumber, pageSize), HttpStatus.OK);
    }

    @GetMapping("/allMoviesPageSort")
    public ResponseEntity<?> getAllMoviesWithPaginationAndSortingHandler(
            @RequestParam(defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
            @RequestParam(defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
            @RequestParam(defaultValue = AppConstants.SORT_DIR,required = false) String dir) {

        return new ResponseEntity<>(movieService.getAllMoviesWithPaginationAndSorting(pageNumber, pageSize,sortBy,dir), HttpStatus.OK);
    }



    private MovieDto convertToMovieDto(String movieDtoObj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        logger.info("entered convertToMovieDto : "+ movieDtoObj);
        logger.info(objectMapper.readValue(movieDtoObj, MovieDto.class).toString());
        return objectMapper.readValue(movieDtoObj, MovieDto.class);
    }

}