package org.rokomari.restResources;

import org.rokomari.exceptions.AppException;
import org.rokomari.models.LoginRequest;
import org.rokomari.models.Role;
import org.rokomari.models.RoleName;
import org.rokomari.models.User;
import org.rokomari.repositories.RoleRepository;
import org.rokomari.repositories.UserRepository;
import org.rokomari.security.JwtTokenProvider;
import org.rokomari.statusCustom.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
@RestController
public class UserResources {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<StatusMessage> loginUser(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        return ResponseEntity
                .ok(new StatusMessage(jwt));

    }

    @PostMapping("/register")
    public ResponseEntity<StatusMessage> registerAUser(@RequestBody User user){
        if(userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity
                    .badRequest()
                    .body(new StatusMessage("Email already taken"));

        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(
                ()-> new AppException("user role not set")
        );

        user.setRoles(Collections.singleton(userRole));

        User inserted = userRepository.save(user);

        return ResponseEntity
                .ok(new StatusMessage("user create successfully"));
    }

}
