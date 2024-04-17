package com.moveflix.MovieAPI.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {


    private Integer movieId;

    @NotBlank(message = "Title is mandatory")
    private String title;


    @NotBlank(message = "director is mandatory")
    private String director;


    @NotBlank(message = "studio is mandatory")
    private String studio;


    private Set<String> movieCast;


    private Integer releaseYear;


    @NotBlank(message = "poster is mandatory")
    private String poster;


    @NotBlank(message = "posterUrl is mandatory")
    private String posterUrl;
}
