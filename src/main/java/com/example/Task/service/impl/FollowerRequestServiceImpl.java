package com.example.Task.service.impl;

import com.example.Task.entity.FollowerRequest;
import com.example.Task.entity.RequestStatus;
import com.example.Task.entity.SocialUser;
import com.example.Task.repository.FollowerRequestRepository;
import com.example.Task.repository.SocialUserRepository;
import com.example.Task.service.FollowerRequestService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class FollowerRequestServiceImpl implements FollowerRequestService {

    @Autowired
    private FollowerRequestRepository followerRequestRepository;

    @Autowired
    private SocialUserRepository socialUserRepository;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    private static final String PROCESS_DEFINITION_KEY = "SocialMedia"; // BPMN ID

    @Override
    public String sendFollowRequest(Long requestById, Long requestToId) {
        SocialUser requestBy = socialUserRepository.findById(requestById).orElse(null);
        SocialUser requestTo = socialUserRepository.findById(requestToId).orElse(null);

        if (requestBy == null || requestTo == null) {
            return "User not found!";
        }

        // Check if a pending request already exists
        Optional<FollowerRequest> existingRequest = followerRequestRepository
                .findByRequestByAndRequestToAndStatus(requestBy, requestTo, RequestStatus.PENDING);

        if (existingRequest.isPresent()) {
            return "You have already sent a follow request!";
        }

        // Save the request in the database (optional, for reference)
        FollowerRequest followRequest = new FollowerRequest();
        followRequest.setRequestBy(requestBy);
        followRequest.setRequestTo(requestTo);
        followRequest.setStatus(RequestStatus.PENDING);
        followerRequestRepository.save(followRequest);

        // Start a Flowable Process
        Map<String, Object> variables = new HashMap<>();
        variables.put("requestById", requestById);
        variables.put("requestToId", requestToId);
        variables.put("requestId", followRequest.getId());

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);

        return "Follow request sent. Process Instance ID: " + processInstance.getId();
    }

    @Override
    public String acceptFollowRequest(Long requestId, Long userId, String flowableProcessId) {
        // Fetch the active task for the given process instance ID
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(flowableProcessId)  // Filter by process instance// Ensure the correct assignee
                .active()                              // Ensure only active tasks are considered
                .list();

        if (tasks.isEmpty()) {
            return "No active follow request task found for approval.";
        }

        Task task = tasks.get(0); // Assuming only one active task per process instance

        Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
        Long requestById = (Long) variables.get("requestById");
        Long requestToId = (Long) variables.get("requestToId");
        Map<String, Object> completeVariables = new HashMap<>();
        completeVariables.put("requestStatus", "ACCEPTED");
        taskService.complete(task.getId(), completeVariables);

        SocialUser requestBy = socialUserRepository.findById(requestById).orElse(null);
        SocialUser requestTo = socialUserRepository.findById(requestToId).orElse(null);

        // Update status in DB
        Optional<FollowerRequest> request = followerRequestRepository.findByRequestByAndRequestToAndStatus(requestBy,requestTo,RequestStatus.PENDING);
        if (request.isPresent()) {
            FollowerRequest followRequest = request.get();
            followRequest.setStatus(RequestStatus.ACCEPTED);
            followerRequestRepository.save(followRequest);

            // Update followers and following
            SocialUser follower = followRequest.getRequestBy();
            SocialUser following = followRequest.getRequestTo();

            follower.getFollowing().add(following);
            following.getFollowers().add(follower);

            socialUserRepository.save(follower);
            socialUserRepository.save(following);

            return "Follow request accepted!";
        }

        return "Follow request record not found!";
    }



@Override
public String rejectFollowRequest(Long requestId, Long userId, String flowableProcessId) {
    // Fetch the active task for the given process instance ID
    List<Task> tasks = taskService.createTaskQuery()
            .processInstanceId(flowableProcessId)  // Filter by process instance
            .active()                              // Ensure only active tasks are considered
            .list();

    if (tasks.isEmpty()) {
        return "No active follow request task found for rejection.";
    }

    Task task = tasks.get(0);



    // Retrieve process variables
    Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
    Long requestById = (Long) variables.get("requestById");
    Long requestToId = (Long) variables.get("requestToId");

    // Complete the task with variables
    Map<String, Object> completeVariables = new HashMap<>();
    completeVariables.put("requestStatus", "REJECTED");
    taskService.complete(task.getId(), completeVariables);

    SocialUser requestBy = socialUserRepository.findById(requestById).orElse(null);
    SocialUser requestTo = socialUserRepository.findById(requestToId).orElse(null);

    // Update status in DB
    Optional<FollowerRequest> request = followerRequestRepository.findByRequestByAndRequestToAndStatus(requestBy,requestTo,RequestStatus.PENDING);
    if (request.isPresent()) {
        FollowerRequest followRequest = request.get();
        followRequest.setStatus(RequestStatus.REJECTED);
        followerRequestRepository.save(followRequest);

        return "Follow request rejected!";
    }

    return "Follow request record not found!";
}


}
