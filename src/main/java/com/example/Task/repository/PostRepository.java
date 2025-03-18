package com.example.Task.repository;

import com.example.Task.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.tag LIKE %:tag%")
    List<Post> findByTag(@Param("tag") String tag);

    @Query("SELECT p FROM Post p WHERE p.author LIKE %:author%")
    List<Post> findByAuthor(@Param("author") String author);
}
