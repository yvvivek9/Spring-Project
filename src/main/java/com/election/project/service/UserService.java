package com.election.project.service;

import com.election.project.entity.User;
import com.election.project.dto.UserDto;

public interface UserService {
    User findByUsername(String username);

    User save(UserDto userDto);

}
