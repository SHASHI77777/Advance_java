package com.example.Task.service.impl;


import com.example.Task.dto.ProfileDto;
import com.example.Task.entity.Profile;
import com.example.Task.exception.ResourceNotFoundException;

import com.example.Task.repository.ProfileRepository;
import com.example.Task.service.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProfileDto getProfileById(long id) throws ResourceNotFoundException {
        Profile profile =profileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("profile id not found"));

        return modelMapper.map(profile,ProfileDto.class);
    }

    @Override
    public ProfileDto updateProfileById(long id, ProfileDto profileDto) throws ResourceNotFoundException {
        Profile profile=profileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("profile id not found"));

        profile.setBio(profileDto.getBio());
        profileRepository.save(profile);
        return modelMapper.map(profile,ProfileDto.class);

    }

    @Override
    public ProfileDto followAnotherId(long id) throws ResourceNotFoundException {
        Profile profile=profileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("profile id not found"));
        profile.setFollowing(profile.getFollowing()+1);
        profileRepository.save(profile);
        return modelMapper.map(profile,ProfileDto.class);
    }

    @Override
    public ProfileDto unfollowAnotherId(long id) throws ResourceNotFoundException {
        Profile profile=profileRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("profile id not found"));
        profile.setFollowing(profile.getFollowing()-1);
        profileRepository.save(profile);
        return modelMapper.map(profile,ProfileDto.class);
    }


}

