package com.example.Task.service;

public interface FollowerRequestService {

    String sendFollowRequest(Long requestById, Long requestToId);

    String acceptFollowRequest(Long requestId, Long userId,String flowableProcessId);

    String rejectFollowRequest(Long requestId, Long userId,String flowableProcessId);
}
