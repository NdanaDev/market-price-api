package com.ephraim.marketpriceapi.service.impl;

import com.ephraim.marketpriceapi.dto.request.CreateAgentRequest;
import com.ephraim.marketpriceapi.dto.response.AgentResponse;
import com.ephraim.marketpriceapi.entity.Agent;
import com.ephraim.marketpriceapi.exception.ResourceNotFoundException;
import com.ephraim.marketpriceapi.mapper.AgentMapper;
import com.ephraim.marketpriceapi.repository.AgentRepository;
import com.ephraim.marketpriceapi.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;
    private final AgentMapper agentMapper;

    @Override
    @Transactional
    public AgentResponse create(CreateAgentRequest request) {
        Agent saved = agentRepository.save(agentMapper.toEntity(request));
        return agentMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgentResponse> getActiveAgents() {
        return agentRepository.findAllByIsActiveTrueOrderByFullNameAsc()
                .stream()
                .map(agentMapper::toResponse)
                .toList();
    }

    @Override
    public Agent getActiveAgent(Long id) {
        return agentRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with id: " + id));
    }
}
