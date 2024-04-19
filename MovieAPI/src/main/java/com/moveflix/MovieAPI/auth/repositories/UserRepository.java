package com.moveflix.MovieAPI.auth.repositories;

import com.moveflix.MovieAPI.auth.enities.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository< User, Integer> {

    Optional<User> findByEmail(String username);
}
