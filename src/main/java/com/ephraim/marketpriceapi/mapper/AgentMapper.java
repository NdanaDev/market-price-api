package com.ephraim.marketpriceapi.mapper;

import com.ephraim.marketpriceapi.dto.request.CreateAgentRequest;
import com.ephraim.marketpriceapi.dto.response.AgentResponse;
import com.ephraim.marketpriceapi.entity.Agent;
import com.ephraim.marketpriceapi.entity.AgentRole;
import org.springframework.stereotype.Component;

@Component
public class AgentMapper {

    public Agent toEntity(CreateAgentRequest request) {
        return Agent.builder()
                .fullName(request.getFullName().trim())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .role(request.getRole() != null ? request.getRole() : AgentRole.FIELD_AGENT)
                .build();
    }

    public AgentResponse toResponse(Agent agent) {
        return AgentResponse.builder()
                .id(agent.getId())
                .fullName(agent.getFullName())
                .phoneNumber(agent.getPhoneNumber())
                .email(agent.getEmail())
                .role(agent.getRole())
                .isActive(agent.isActive())
                .createdAt(agent.getCreatedAt())
                .updatedAt(agent.getUpdatedAt())
                .build();
    }
}
