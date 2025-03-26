package com.example.Task.dto;

import com.example.Task.entity.RequestStatus;  // Import the missing enum
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowerRequestDto {

    private Long id;
    private Long requestByUserId;
    private Long requestToUserId;
    private RequestStatus status;
    private LocalDateTime requestDate;
}
