package com.election.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.election.project.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User save(User user);
}
