package org.rokomari.services;

import org.rokomari.models.User;
import org.rokomari.models.UserPrincipal;
import org.rokomari.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    UserRepository repository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = repository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(
                        ()-> new UsernameNotFoundException("User with given username or email not found.")
                );
        return UserPrincipal.create(user);

    }

    @Transactional
    //we will use this method inside JwtAuthenticationFilter
    public UserDetails loadUserById(int id){
        User user = repository.findById(id).orElseThrow(
                ()-> new UsernameNotFoundException("User not found with id: "+id)
        );
        return UserPrincipal.create(user);
    }


}
