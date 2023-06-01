package com.Kartikey.testbackend.config;

import com.Kartikey.testbackend.dao.UserRepository;
import com.Kartikey.testbackend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByName(username);
        return user.map(UserInfoUserDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException("user not found " + username));
    }
}
