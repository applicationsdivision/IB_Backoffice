package com.backoffice.BackofficeInternetBanking.Controller;


import com.backoffice.BackofficeInternetBanking.Entity.User;
import com.backoffice.BackofficeInternetBanking.Service.UserService;
import com.backoffice.BackofficeInternetBanking.dao.UserRepository;
import com.backoffice.BackofficeInternetBanking.dto.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.backoffice.BackofficeInternetBanking.exceptions.UserNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RestController
@RequestMapping("api/user")
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
            System.out.println(username);
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

    @GetMapping("allusers")

    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users=userRepository.findAll();
        return  new ResponseEntity<>(users,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> user=userRepository.findById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/check-session")
    public ResponseEntity<Map<String, Boolean>> checkSession(HttpSession session) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("isAuthenticated", session.getAttribute("user") != null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        System.out.println(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User existingUser = optionalUser.get();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());

        try {
            userRepository.save(existingUser);
            return ResponseEntity.ok(existingUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
