package com.ephraim.marketpriceapi.dto.request;

import com.ephraim.marketpriceapi.entity.AgentRole;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAgentRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    private String phoneNumber;

    private String email;

    private AgentRole role = AgentRole.FIELD_AGENT;
}
