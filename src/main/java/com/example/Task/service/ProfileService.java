package com.example.Task.service;


import com.example.Task.dto.ProfileDto;
import com.example.Task.exception.ResourceNotFoundException;

public interface ProfileService {
    ProfileDto getProfileById(long id) throws ResourceNotFoundException;

    ProfileDto updateProfileById(long id, ProfileDto profileDto) throws ResourceNotFoundException;

    ProfileDto followAnotherId(long id) throws ResourceNotFoundException;

    ProfileDto unfollowAnotherId(long id) throws ResourceNotFoundException;
}
