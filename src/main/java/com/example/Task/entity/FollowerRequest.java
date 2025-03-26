package com.example.Task.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "request_by_user_id", nullable = false)
    private SocialUser requestBy;  // User who sends the request

    @ManyToOne
    @JoinColumn(name = "request_to_user_id", nullable = false)
    private SocialUser requestTo;  // User who receives the request

    @Enumerated(EnumType.STRING)
    private RequestStatus status;  // PENDING, ACCEPTED, REJECTED

    @CreationTimestamp
    private LocalDateTime requestDate;



}
