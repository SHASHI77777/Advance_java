package com.example.Task.dto;

import lombok.Data;

import java.util.Set;

@Data
public class ProfileDto {
    private long profileId;
    private String bio;
    private long noOfPosts;
    private Set<Long> followers;  // Store only profile IDs
    private Set<Long> following;  // Store only profile IDs
}
