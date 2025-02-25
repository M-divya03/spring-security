package com.example.SpringSecurity.repo;

import com.example.SpringSecurity.entity.Role;
import com.example.SpringSecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByEmail(String username);

    User findByRole(Role role);

   Optional<User> findByPassword(String password);

}
