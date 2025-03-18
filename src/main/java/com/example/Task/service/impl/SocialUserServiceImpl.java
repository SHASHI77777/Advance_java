package com.example.Task.service.impl;

import com.example.Task.dto.SocialUserDto;
import com.example.Task.entity.Profile;
import com.example.Task.entity.SocialUser;
import com.example.Task.exception.UserNotFoundException;
import com.example.Task.repository.ProfileRepository;
import com.example.Task.repository.SocialUserRepository;
import com.example.Task.service.JWTService;
import com.example.Task.service.SocialUserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SocialUserServiceImpl implements SocialUserService {

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private SocialUserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    /**
     * ✅ Register a new user
     */
    @Override
    public SocialUserDto registerUser(SocialUserDto socialUserDto) {
        socialUserDto.setPassword(encoder.encode(socialUserDto.getPassword()));
        SocialUser user = modelMapper.map(socialUserDto, SocialUser.class);
        SocialUser savedUser = userRepository.save(user);

        Profile profile = new Profile();
        profile.setBio(socialUserDto.getBio());
        profile.setUser(savedUser);
        profileRepository.save(profile);

        return modelMapper.map(savedUser, SocialUserDto.class);
    }

    /**
     * ✅ Get user details by ID
     */
    @Override
    public SocialUserDto getUserById(long userId) {
        SocialUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
        return modelMapper.map(user, SocialUserDto.class);
    }

    /**
     * ✅ Update user details
     */
    @Override
    public SocialUserDto updateUser(long userId, SocialUserDto socialUserDto) {
        SocialUser user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        user.setEmail(socialUserDto.getEmail());
        user.setName(socialUserDto.getName());
        if (socialUserDto.getPassword() != null) {
            user.setPassword(encoder.encode(socialUserDto.getPassword()));
        }

        userRepository.save(user);
        return modelMapper.map(user, SocialUserDto.class);
    }

    /**
     * ✅ Get all users with pagination
     */
    @Override
    public Page<SocialUser> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, Boolean sortOrder) {
        Sort sort = sortOrder ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return userRepository.findAll(pageable);
    }

    /**
     * ✅ Verify User Credentials & Generate JWT Token
     */
    @Override
    public String verifyUser(SocialUserDto socialUserDto) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(socialUserDto.getEmail(), socialUserDto.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(socialUserDto.getEmail(), socialUserDto.getUserId());
        } else {
            return "Authentication failed";
        }
    }

    /**
     * ✅ Get User ID by Email
     */
    @Override
    public Long getUserIdByEmail(String email) {
        Optional<SocialUser> userOptional = userRepository.findByEmail(email);
        return userOptional.map(SocialUser::getUserId)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }
}
