package com.example.Task.service;

import com.example.Task.dto.PostDto;
import com.example.Task.exception.ResourceNotFoundException;

import java.util.List;

public interface PostService {

    PostDto createPost(PostDto postDto , long id);

    PostDto getById(Long id);

    List<PostDto>getAllPosts();

    PostDto update(Long postid,PostDto postDto) throws ResourceNotFoundException;

    String deleteById(Long id);
    List<PostDto> filterByTag(String tag);
    List<PostDto> filterByAuthor(String author);
}
