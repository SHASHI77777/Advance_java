package com.example.Task.dto;


import com.example.Task.entity.SocialUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String author;
    private String tag;
    private String content;
    private LocalDateTime createDat;
    private SocialUser user;
}