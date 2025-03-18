package com.example.Task.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUserDto {


    private long userId;
    private String name;
    private String email;
    private String contactNo;
    private String password;
    private String bio;
    private LocalDateTime createDate;



}
