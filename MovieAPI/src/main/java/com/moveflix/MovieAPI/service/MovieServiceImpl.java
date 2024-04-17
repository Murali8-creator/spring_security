package com.moveflix.MovieAPI.service;

import com.moveflix.MovieAPI.dto.MovieDto;
import com.moveflix.MovieAPI.enitities.Movie;
import com.moveflix.MovieAPI.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class MovieServiceImpl implements MovieService{

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
        String uploadedFileName = fileService.uploadFile(path, multipartFile);

        //2.set the value of field 'poster' as the name of the file
        movieDto.setPoster(uploadedFileName);

        //3.map the movieDto to movie entity
        Movie movie = new Movie(
                movieDto.getMovieId(),
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
        MovieDto response = new MovieDto(
                 savedMovie.getMovieId(),
                 savedMovie.getTitle(),
                 savedMovie.getDirector(),
                 savedMovie.getStudio(),
                 savedMovie.getMovieCast(),
                 savedMovie.getReleaseYear(),
                 savedMovie.getPoster(),
                    posterUrl
        );
        return response;
    }

    @Override
    public MovieDto getMovieById(int movieId) {
        //1.check the data in db and if exists,fetch the data of given ID
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new RuntimeException("Movie not found"));

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
}
