

package com.ecommerce.usermicroservice.repository;

import com.ecommerce.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

//1. Basic CRUD Operations from JpaRepositor