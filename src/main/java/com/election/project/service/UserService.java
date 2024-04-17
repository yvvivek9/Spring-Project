package com.election.project.service;

import com.election.project.entity.User;

public interface UserService {
    User findByUsername(String username);

    User save(User user);

}
