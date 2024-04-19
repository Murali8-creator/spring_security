package com.moveflix.MovieAPI.repositories;

import com.moveflix.MovieAPI.enitities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
}
