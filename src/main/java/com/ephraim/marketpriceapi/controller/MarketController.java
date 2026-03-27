package com.ephraim.marketpriceapi.controller;

import com.ephraim.marketpriceapi.dto.request.CreateMarketRequest;
import com.ephraim.marketpriceapi.dto.response.MarketResponse;
import com.ephraim.marketpriceapi.service.MarketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/markets")
@RequiredArgsConstructor
@Tag(name = "Markets", description = "Manage markets")
public class MarketController {

    private final MarketService marketService;

    @PostMapping
    @Operation(summary = "Create a new market")
    public ResponseEntity<MarketResponse> create(@Valid @RequestBody CreateMarketRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marketService.create(request));
    }

    @GetMapping
    @Operation(summary = "List all active markets")
    public ResponseEntity<List<MarketResponse>> listActive() {
        return ResponseEntity.ok(marketService.listActive());
    }
}
