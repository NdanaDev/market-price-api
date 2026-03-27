package com.ephraim.marketpriceapi.dto.response;

import com.ephraim.marketpriceapi.entity.AgentRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AgentResponse {

    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private AgentRole role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
