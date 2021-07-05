package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.User;
import com.mountblue.hackernews.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {

    public void saveUser(User user);

//    public User findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

//    public User getLoggedUser() {
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String username = "";
//        if (principal instanceof UserDetails) {
//            username = ((UserDetails) principal).getUsername();
//        }
//        return this.findByEmail(username);
//    }

//    public List<User> findAllUsers() {
//        return userRepository.findAll();
//    }

//    public User findById(int userId) {
//        return userRepository.findById(userId);
//    }

//    public void deleteUser(int userId) {
//        userRepository.deleteById(userId);
//    }

}
