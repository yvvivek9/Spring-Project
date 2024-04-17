package com.election.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.election.project.entity.User;
import com.election.project.dto.UserDto;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User save(UserDto userDto);
}
