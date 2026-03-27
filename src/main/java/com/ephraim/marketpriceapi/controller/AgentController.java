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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agents")
@RequiredArgsConstructor
@Tag(name = "Agents", description = "Manage trusted data submission agents")
public class AgentController {

    private final AgentService agentService;

    @GetMapping
    @Operation(summary = "List all active agents")
    public ResponseEntity<List<AgentResponse>> listActive() {
        return ResponseEntity.ok(agentService.getActiveAgents());
    }

    @PostMapping
    @Operation(summary = "Create a new agent")
    public ResponseEntity<AgentResponse> create(@Valid @RequestBody CreateAgentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(agentService.create(request));
    }
}
