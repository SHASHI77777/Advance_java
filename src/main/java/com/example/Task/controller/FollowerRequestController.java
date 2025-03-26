package com.example.Task.controller;

import com.example.Task.service.FollowerRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow-requests")
public class FollowerRequestController {

    @Autowired
    private FollowerRequestService followerRequestService;

    // Send a follow request
    @PostMapping("/send/{requestById}/{requestToId}")
    public String sendFollowRequest(@PathVariable Long requestById, @PathVariable Long requestToId) {
        return followerRequestService.sendFollowRequest(requestById, requestToId);
    }

    // Accept a follow request (only the receiving user can accept)
    @PutMapping("/accept/{requestId}/{userId}/{flowableProcessId}")
    public String acceptFollowRequest(@PathVariable Long requestId, @PathVariable Long userId,@PathVariable String flowableProcessId) {
        return followerRequestService.acceptFollowRequest(requestId, userId,flowableProcessId);
    }

    // Reject a follow request (only the receiving user can reject)

    @PutMapping("/reject/{requestId}/{userId}/{flowableProcessId}")
    public String rejectFollowRequest(@PathVariable Long requestId, @PathVariable Long userId,@PathVariable String flowableProcessId) {
        return followerRequestService.rejectFollowRequest(requestId, userId,flowableProcessId);
    }
}
