package com.moveflix.MovieAPI.auth.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenRepository,Integer> {
}
