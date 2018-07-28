package org.rokomari.restResources;

import org.rokomari.customPayloadsAndMessages.LoginResponse;
import org.rokomari.customPayloadsAndMessages.RegisterResponse;
import org.rokomari.exceptions.AppException;
import org.rokomari.customPayloadsAndMessages.LoginRequest;
import org.rokomari.models.Role;
import org.rokomari.models.RoleName;
import org.rokomari.models.User;
import org.rokomari.repositories.RoleRepository;
import org.rokomari.repositories.UserRepository;
import org.rokomari.security.JwtTokenProvider;
import org.rokomari.customPayloadsAndMessages.StatusMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * Created by Abdullah Al Amin on 7/26/2018.
 */
@RestController
public class AuthenticationResources {

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
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                ()-> new UsernameNotFoundException("user with given email doesnot exists")
        );
        HttpHeaders header = new HttpHeaders();
        header.add("jwt_token","Bearer "+jwt);
        LoginResponse response = new LoginResponse();
        response.setStatus("logged_in");
        response.setFirstName(user.getFirstName());
        response.setEmail(user.getEmail());

        return ResponseEntity
                .ok()
                .headers(header)
                .body(response);

    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerAUser(@RequestBody User user){
        if(userRepository.existsByEmail(user.getEmail())){
            RegisterResponse registerResponse = new RegisterResponse();
            registerResponse.setStatus("email alreay taken");
            return ResponseEntity
                    .badRequest()
                    .body(registerResponse);

        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByRoleName(RoleName.ROLE_USER).orElseThrow(
                ()-> new AppException("user role not set")
        );

        user.setRoles(Collections.singleton(userRole));

        User inserted = userRepository.save(user);

        return ResponseEntity
                .ok(new RegisterResponse(
                        inserted.getFirstName(),inserted.getLastName(),
                        inserted.getEmail(),inserted.getMobile(),"success"
                ));
    }

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

}
