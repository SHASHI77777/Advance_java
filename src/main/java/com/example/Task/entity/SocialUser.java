package com.example.Task.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    private String name;
    private String email;
    private String contactNo;
    private String password;

    @CreationTimestamp
    private LocalDateTime createDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> post;

    @OneToMany(mappedBy = "requestBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FollowerRequest> sentRequests;  // Requests this user sent

    @OneToMany(mappedBy = "requestTo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FollowerRequest> receivedRequests;  // Requests this user received

    // ✅ NEW: Followers and Following Lists
    @ManyToMany
    @JoinTable(
            name = "user_following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<SocialUser> following;

    @ManyToMany(mappedBy = "following")
    private List<SocialUser> followers;
}
