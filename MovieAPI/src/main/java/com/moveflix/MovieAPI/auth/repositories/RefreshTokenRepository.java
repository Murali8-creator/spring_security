package com.moveflix.MovieAPI.auth.repositories;

import com.moveflix.MovieAPI.auth.enities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Integer> {
    Optional<RefreshToken> findByRefreshToken(String token);
}
