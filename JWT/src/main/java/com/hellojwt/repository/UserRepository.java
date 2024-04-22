package com.hellojwt.repository;

import com.hellojwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUserName(String userName);
}