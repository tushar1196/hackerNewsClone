package com.mountblue.hackernews.service;

import com.mountblue.hackernews.model.User;
import com.mountblue.hackernews.repository.UserRepository;
import com.mountblue.hackernews.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName);
        if (user == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }
        return new MyUserDetails(user);
    }
}
