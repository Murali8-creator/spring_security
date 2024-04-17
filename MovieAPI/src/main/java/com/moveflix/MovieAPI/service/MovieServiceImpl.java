package com.moveflix.MovieAPI.service;

import com.moveflix.MovieAPI.dto.MovieDto;
import com.moveflix.MovieAPI.dto.MoviePageResponse;
import com.moveflix.MovieAPI.enitities.Movie;
import com.moveflix.MovieAPI.exceptions.FileExistsException;
import com.moveflix.MovieAPI.exceptions.MovieNotFoundException;
import com.moveflix.MovieAPI.repositories.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Service
public class MovieServiceImpl implements MovieService{

    private final Logger logger = LoggerFactory.getLogger(MovieServiceImpl.class);
    private final MovieRepository movieRepository;

    private final FileService fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileService fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }
    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile multipartFile) throws IOException {

        //1.upload the file
        if (Files.exists(Paths.get(path + File.separator + multipartFile.getOriginalFilename()))) {
            throw new FileExistsException("File already exists");
        }
        String uploadedFileName = fileService.uploadFile(path, multipartFile);

        //2.set the value of field 'poster' as the name of the file
        movieDto.setPoster(uploadedFileName);

        //3.map the movieDto to movie entity
        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        //4.save the movie entity
        Movie savedMovie = movieRepository.save(movie);

        //5.generate the posterUrl
        String posterUrl = baseUrl+"/file/"+uploadedFileName;

        //6.map Movie object to DTO object and return it
        return new MovieDto(
                 savedMovie.getMovieId(),
                 savedMovie.getTitle(),
                 savedMovie.getDirector(),
                 savedMovie.getStudio(),
                 savedMovie.getMovieCast(),
                 savedMovie.getReleaseYear(),
                 savedMovie.getPoster(),
                    posterUrl
        );
    }

    @Override
    public MovieDto getMovieById(int movieId) {
        //1.check the data in db and if exists,fetch the data of given ID
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found"));

        //2. generate posterUrl
        String posterUrl = baseUrl+"/file/"+movie.getPoster();

        //3. map the Movie object to DTO object and return it
        return new MovieDto(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getDirector(),
                movie.getStudio(),
                movie.getMovieCast(),
                movie.getReleaseYear(),
                movie.getPoster(),
                posterUrl
        );
    }

    @Override
    public List<MovieDto> getAllMovies() {
        //1.fetch all the data from db
        List<Movie> movies = movieRepository.findAll();

        List<MovieDto> movieDtos = new ArrayList<>();

        //2.generate posterUrl for each movie
        for(Movie movie : movies){
            String posterUrl = baseUrl+"/file/"+movie.getPoster();
            movieDtos.add(new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            ));
        }
        return movieDtos;
    }

    @Override
    public MoviePageResponse getAllMoviesWithPagination(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);


        Page<Movie> moviePages = movieRepository.findAll(pageable);//info about the pages
        logger.info("moviePages : "+moviePages);

        List<Movie> movies = moviePages.getContent();
        logger.info("movies : "+movies);

        List<MovieDto> movieDtos = new ArrayList<>();

        for(Movie movie : movies){
            String posterUrl = baseUrl+"/file/"+movie.getPoster();
            movieDtos.add(new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            ));
        }

        return new MoviePageResponse(movieDtos, pageNumber, pageSize, (int) moviePages.getTotalElements(), moviePages.getTotalPages(), moviePages.isLast());
    }

    @Override
    public MoviePageResponse getAllMoviesWithPaginationAndSorting(Integer pageNumber, Integer pageSize, String sortBy, String dir) {

        Sort sort = dir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);


        Page<Movie> moviePages = movieRepository.findAll(pageable);//info about the pages
        logger.info("moviePages : "+moviePages);

        List<Movie> movies = moviePages.getContent();
        logger.info("movies : "+movies);

        List<MovieDto> movieDtos = new ArrayList<>();

        for(Movie movie : movies){
            String posterUrl = baseUrl+"/file/"+movie.getPoster();
            movieDtos.add(new MovieDto(
                    movie.getMovieId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            ));
        }

        return new MoviePageResponse(movieDtos, pageNumber, pageSize, (int) moviePages.getTotalElements(), moviePages.getTotalPages(), moviePages.isLast());
    }

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile multipartFile) throws IOException {

        //1.check if movie exists in db with given movie id
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new MovieNotFoundException("Movie not found with id : "+movieId));


        //2.if file is null, do nothing
        //if file is not null , then delete existing file and upload new file
        String fileName = movie.getPoster();
        if(multipartFile != null){
            Files.deleteIfExists(Paths.get(path + File.separator + fileName));
            fileName = fileService.uploadFile(path, multipartFile);
        }

        //3.set movieDto's poster value , according to step2
        movieDto.setPoster(fileName);

        //4.map it to Movie object
        Movie movie1 = new Movie(
                movie.getMovieId(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );

        //5.save the movie object
        Movie savedMovie = movieRepository.save(movie1);

        //6.generate posterUrl
        String posterUrl = baseUrl+"/file/"+movie1.getPoster();

        return new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        //1.check if movie exists in db with given movie id
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found with id : "+movieId));

        //2.delete the file from the file system
        Files.deleteIfExists(Paths.get(path + File.separator + movie.getPoster()));


        //3.delete the movie from the db
        movieRepository.deleteById(movieId);

        return "Movie deleted successfully of id : "+movieId;
    }
}
