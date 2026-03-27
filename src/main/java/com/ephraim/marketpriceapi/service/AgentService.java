package com.ephraim.marketpriceapi.service;

import com.ephraim.marketpriceapi.dto.request.CreateAgentRequest;
import com.ephraim.marketpriceapi.dto.response.AgentResponse;
import com.ephraim.marketpriceapi.entity.Agent;

import java.util.List;

public interface AgentService {

    AgentResponse create(CreateAgentRequest request);

    List<AgentResponse> getActiveAgents();

    Agent getActiveAgent(Long id);
}
