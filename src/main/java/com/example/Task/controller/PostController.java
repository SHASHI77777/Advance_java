package com.example.Task.controller;

import com.example.Task.dto.PostDto;
import com.example.Task.exception.ResourceNotFoundException;
import com.example.Task.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api") // ✅ Base path set as "/api"
public class PostController {

    @Autowired
    private PostService service;

//    /** ✅ Create Post - Uses `/api/create?userId={id}` */
////    @PostMapping("/create")
////    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestParam long userId) {
////        PostDto post = service.createPost(postDto, userId);
////        return new ResponseEntity<>(post, HttpStatus.CREATED);
////    }


@PostMapping("/create")
public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @RequestParam long userId) {
    PostDto post = service.createPost(postDto, userId);
    return new ResponseEntity<>(post, HttpStatus.CREATED);
}


    /**
     * ✅ Get Post by ID
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable("id") Long id) {
        PostDto postDto = service.getById(id);
        return ResponseEntity.ok(postDto);
    }

    /**
     * ✅ View All Posts
     */
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> allPosts = service.getAllPosts();
        return ResponseEntity.ok(allPosts);
    }

    /** ✅ Update Post - Uses `/api/posts/update/{id}` */
    @PutMapping("/posts/update/{id}")
    public ResponseEntity<PostDto> update(@PathVariable("id") Long postId, @RequestBody PostDto postDto) {
        PostDto updatedPost = service.update(postId, postDto);
        return ResponseEntity.ok(updatedPost);
    }

    /** ✅ Delete Post - Uses `/api/posts/delete/{id}` */
    @DeleteMapping("/posts/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /** ✅ Filter Posts by Tag - Uses `/api/posts/filter/tag?tag=Java` */
    @GetMapping("/posts/filter/tag")
    public ResponseEntity<List<PostDto>> filterByTag(@RequestParam String tag) {
        List<PostDto> posts = service.filterByTag(tag);
        return ResponseEntity.ok(posts);
    }

    /** ✅ Filter Posts by Author - Uses `/api/posts/filter/author?author=Jane Doe` */
    @GetMapping("/posts/filter/author")
    public ResponseEntity<List<PostDto>> filterByAuthor(@RequestParam String author) {
        List<PostDto> posts = service.filterByAuthor(author);
        return ResponseEntity.ok(posts);
    }
}
