package com.ephraim.marketpriceapi.service;

import com.ephraim.marketpriceapi.dto.request.CreateAgentRequest;
import com.ephraim.marketpriceapi.dto.response.AgentResponse;
import com.ephraim.marketpriceapi.entity.Agent;

public interface AgentService {

    AgentResponse create(CreateAgentRequest request);

    Agent getActiveAgent(Long id);
}
