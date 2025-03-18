package com.example.Task.service;

import com.example.Task.dto.SocialUserDto;
import com.example.Task.entity.SocialUser;
import com.example.Task.exception.ResourceNotFoundException;
import org.springframework.data.domain.Page;

public interface SocialUserService {

    SocialUserDto registerUser(SocialUserDto socialUserDto);
    SocialUserDto getUserById(long userId) throws ResourceNotFoundException;
    SocialUserDto updateUser(long userId, SocialUserDto socialUserDto) throws ResourceNotFoundException;
    Page<SocialUser> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, Boolean sortOrder);
    String verifyUser(SocialUserDto socialUserDto);
    Long getUserIdByEmail(String email);
}



































//package com.example.Task.service;
//
//
//import com.example.Task.dto.SocialUserDto;
//import com.example.Task.entity.SocialUser;
//import com.example.Task.exception.ResourceNotFoundException;
//import org.springframework.data.domain.Page;
//
//import java.util.List;
//
//public interface SocialUserService{
//
//    SocialUserDto registerSocialUser(SocialUserDto socialUserDto);
//    SocialUserDto getUserDetailsById(long userId) throws ResourceNotFoundException;
//    SocialUserDto updateSocialUser(long userId, SocialUserDto socialUserDto) throws ResourceNotFoundException;
//
//    Page<SocialUser> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, Boolean sortOrder);
//
//    String verify(SocialUserDto socialUserDto);
//
//
//    Long getUserIdByEmail(String email);
//}
//
