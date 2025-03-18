



package com.example.Task.controller;

import com.example.Task.dto.SocialUserDto;
import com.example.Task.entity.SocialUser;
import com.example.Task.exception.ResourceNotFoundException;
import com.example.Task.exception.UserNotFoundException;
import com.example.Task.service.JWTService;
import com.example.Task.service.SocialUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/users")
public class SocialUserController {

    @Autowired
    private SocialUserService socialUserService;

    @Autowired
    private JWTService jwtService;

    /**
     * ✅ User Login API: Authenticates user and returns token with userId.
     */
    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody SocialUserDto socialUserDto) {
        String token = socialUserService.verifyUser(socialUserDto);
        if (token.equals("Authentication failed")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        Long userId = socialUserService.getUserIdByEmail(socialUserDto.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", userId);

        return ResponseEntity.ok(response);
    }

    /**
     * ✅ User Registration API
     */
    @PostMapping("/register")
    public ResponseEntity<SocialUserDto> registerUser(@RequestBody SocialUserDto socialUserDto) {
        return new ResponseEntity<>(socialUserService.registerUser(socialUserDto), HttpStatus.CREATED);
    }

    /**
     * ✅ Get User Details by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<SocialUserDto> getUserById(@PathVariable("id") long userId) throws ResourceNotFoundException {
        return new ResponseEntity<>(socialUserService.getUserById(userId), HttpStatus.OK);
    }

    /**
     * ✅ Update User Details
     */
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<SocialUserDto> updateUser(@PathVariable("id") long userId, @RequestBody SocialUserDto socialUserDto) throws UserNotFoundException {
        return new ResponseEntity<>(socialUserService.updateUser(userId, socialUserDto), HttpStatus.OK);
    }

    /**
     * ✅ Get All Users with Pagination
     */
    @GetMapping("/getAllUsers")
    public Page<SocialUser> getAllUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "true") boolean ascending) {
        return socialUserService.getAllUsers(page, size, sortBy, ascending);
    }
}























//package com.example.Task.controller;
//import com.example.Task.dto.SocialUserDto;
//import com.example.Task.entity.SocialUser;
//import com.example.Task.exception.ResourceNotFoundException;
//import com.example.Task.exception.UserNotFoundException;
//import com.example.Task.service.SocialUserService;
//import com.example.Task.service.impl.SocialUserServiceImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//
//@CrossOrigin(origins = "http://localhost:4200")
//@RequestMapping("/users")
//public class SocialUserController {
//
//    @Autowired
//    private SocialUserService socialUserService;
//
//@Autowired
//    private SocialUserServiceImpl socialImpl;
////    @PostMapping("/Login")
////    public String login(@RequestBody SocialUserDto socialUserDto) {
////        socialUserDto.getUserId();
////        return socialImpl.verify(socialUserDto);
////    }
//
//
//
////    @PostMapping("/Login")
////    public String login(@RequestBody SocialUserDto socialUserDto) {
////        socialUserDto.getUserId();
////        return socialImpl.verify(socialUserDto);
////    }
//
//
//
//
//
//    @PostMapping("/register")
//    public ResponseEntity<SocialUserDto>registerSocialUser(@RequestBody SocialUserDto socialUserDto)
//    {
//        socialUserDto.setBio(socialUserDto.getBio());
//        return new ResponseEntity<>(socialUserService.registerSocialUser(socialUserDto), HttpStatus.CREATED);
//    }
//
//    //GETMAPPING  /users/{id}-get user details based on id
//    @GetMapping("/{id}")
//    public ResponseEntity<SocialUserDto>getUserDetailsById(@PathVariable("id") long userId) throws ResourceNotFoundException {
//
//        return new ResponseEntity<>(socialUserService.getUserDetailsById(userId),HttpStatus.OK);
//    }
//
//    //PUT /users/updateUser/{id}--update user Details
//    @PutMapping("/updateUser/{id}")
//    public ResponseEntity<SocialUserDto>updateSocialUser(@PathVariable("id") long userId, @RequestBody SocialUserDto socialUserDto) throws UserNotFoundException {
//        return new ResponseEntity<>(socialUserService.updateSocialUser(userId,socialUserDto), HttpStatus.CREATED);
//    }
//
//
//    //GETMAPPING /user/getAllUsers---get all users use pagination here
//    @GetMapping("/getAllUsers")
//    public Page<SocialUser> getAllUsers(
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "5") int size,
//            @RequestParam(defaultValue = "userId") String sortBy,
//            @RequestParam(defaultValue = "true") boolean ascending)
//    {
//        return socialUserService.getAllUsers(page,size,sortBy,ascending);
//
//    }
//}
