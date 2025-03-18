package com.example.Task.controller;


import com.example.Task.dto.ProfileDto;
import com.example.Task.exception.ResourceNotFoundException;
import com.example.Task.service.ProfileService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    //  /profiles/{id}--get profile details based on id
    @GetMapping("/{id}")
    public ResponseEntity<ProfileDto>getProfileById(@PathVariable("id")long id) throws ResourceNotFoundException
    {
        return new ResponseEntity<>(profileService.getProfileById(id), HttpStatus.OK);
    }

    // PUT /profiles/{id}: Update profile bio Based on id
    @PutMapping("/{id}/update")
    public ResponseEntity<ProfileDto>updateProfileBioById(@PathVariable("id")long id,@RequestBody ProfileDto profileDto) throws ResourceNotFoundException {
        return new ResponseEntity<>( profileService.updateProfileById(id,profileDto),HttpStatus.OK);
    }

    //POST /profiles/{id}/follow: Follow another id
    @PostMapping("{id}/follow")
    public ResponseEntity<ProfileDto>followAnotherId(@PathVariable("id")long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(profileService.followAnotherId(id),HttpStatus.OK);

    }

    //POST /profiles/{id}/unfollow: Unfollow a id
    @PostMapping("{id}/unfollow")
    public ResponseEntity<ProfileDto>unfollowAnotherId(@PathVariable("id")long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(profileService.unfollowAnotherId(id),HttpStatus.OK);
    }
}
