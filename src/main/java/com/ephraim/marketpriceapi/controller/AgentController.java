package com.ephraim.marketpriceapi.controller;

import com.ephraim.marketpriceapi.dto.request.CreateAgentRequest;
import com.ephraim.marketpriceapi.dto.response.AgentResponse;
import com.ephraim.marketpriceapi.service.AgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/agents")
@RequiredArgsConstructor
@Tag(name = "Agents", description = "Manage trusted data submission agents")
public class AgentController {

    private final AgentService agentService;

    @PostMapping
    @Operation(summary = "Create a new agent")
    public ResponseEntity<AgentResponse> create(@Valid @RequestBody CreateAgentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agentService.create(request));
    }
}
