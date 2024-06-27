package com.backoffice.BackofficeInternetBanking.Controller;


import com.backoffice.BackofficeInternetBanking.Entity.User;
import com.backoffice.BackofficeInternetBanking.Service.UserService;
import com.backoffice.BackofficeInternetBanking.dao.UserRepository;
import com.backoffice.BackofficeInternetBanking.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import com.backoffice.BackofficeInternetBanking.exceptions.UserNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.Optional;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("api/")
public class UserController {


    private UserRepository userRepository;
    private UserService userService;

    @Autowired
    public UserController(UserRepository userRepository,UserService userService)
    {
        this.userRepository=userRepository;
        this.userService=userService;
    }
    @PostMapping("login")
    public  ResponseEntity<String> login(@RequestBody loginDto loginDto){
        try {


            String username = "bnr\\\\" + loginDto.getUsername();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));
            ResponseEntity<String> response = userService.authenticateUser(loginDto.getUsername(), loginDto.getPassword());
            if(response.getStatusCode()== HttpStatus.UNAUTHORIZED){

                return new ResponseEntity<String>("Invalid credentils",HttpStatus.UNAUTHORIZED );
            }
            System.out.println("authenticate");
            return response;
        }
        catch (BadCredentialsException e) {
           return new ResponseEntity<String>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody registerDto registerDto){
        String username = "bnr\\\\" + registerDto.getUsername();
        System.out.println("Checking for username: " + username);
        if(userRepository.existsByusername(username)){
            return new ResponseEntity<>("Username Exists", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(username);
        user.setEmail(registerDto.getEmail());
        System.out.println(user);
        userRepository.save(user);
        return new ResponseEntity<>("User Registered Successfull!", HttpStatus.OK);
    }
}
