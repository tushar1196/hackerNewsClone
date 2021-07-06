package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.User;

public interface UserService {

    void saveUser(User user);

    User findByEmail(String email);
}
