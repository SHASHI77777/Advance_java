package com.example.Task.repository;

import com.example.Task.entity.SocialUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SocialUserRepository extends JpaRepository<SocialUser,Long> {

//    SocialUser findByEmail(String username);

    Optional<SocialUser> findByEmail(String email);

    @Query("SELECT u FROM SocialUser u WHERE NOT EXISTS " +
            "(SELECT p FROM Post p WHERE p.user = u AND p.createDat >= :cutoffDate)")
    List<SocialUser> findUsersWithNoRecentPosts(LocalDateTime cutoffDate);
}