package com.example.Task.service.impl;

import com.example.Task.dto.PostDto;
import com.example.Task.entity.Post;
import com.example.Task.entity.SocialUser;
import com.example.Task.exception.ResourceNotFoundException;
import com.example.Task.exception.UserNotFoundException;
import com.example.Task.repository.PostRepository;
import com.example.Task.repository.SocialUserRepository;
import com.example.Task.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SocialUserRepository socialUserRepository;

    @Override
    public PostDto createPost(PostDto postDto , long userId) {
        SocialUser user = socialUserRepository.findById(userId).
                orElseThrow(()-> new UserNotFoundException(" User not found "));
        Post post = modelMapper.map(postDto, Post.class );
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto getById(Long id)  throws UserNotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post not found"));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getAllPosts() {

        List<Post> postList = postRepository.findAll();
        return postList.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDto update(Long postid, PostDto postDto) throws ResourceNotFoundException {

        Post post = postRepository.findById(postid)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));

        post.setAuthor(postDto.getAuthor());
        post.setTag(postDto.getTag());
        post.setContent(postDto.getContent());
        post.setTitle(postDto.getTitle());
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public String deleteById(Long id) {
        postRepository.deleteById(id);
        return "Post deleted successfully";
    }

    @Override
    public List<PostDto> filterByTag(String tag) {
        List<Post> postList = postRepository.findByTag(tag);
        return postList.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostDto> filterByAuthor(String author) {
        List<Post> postList = postRepository.findByAuthor(author);
        return postList.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());
    }
}
