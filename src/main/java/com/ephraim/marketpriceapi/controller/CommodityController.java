package com.ephraim.marketpriceapi.controller;

import com.ephraim.marketpriceapi.dto.request.CreateCommodityRequest;
import com.ephraim.marketpriceapi.dto.response.CommodityResponse;
import com.ephraim.marketpriceapi.service.CommodityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commodities")
@RequiredArgsConstructor
@Tag(name = "Commodities", description = "Manage commodities")
public class CommodityController {

    private final CommodityService commodityService;

    @PostMapping
    @Operation(summary = "Create a new commodity")
    public ResponseEntity<CommodityResponse> create(@Valid @RequestBody CreateCommodityRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commodityService.create(request));
    }

    @GetMapping
    @Operation(summary = "List all active commodities")
    public ResponseEntity<List<CommodityResponse>> listActive() {
        return ResponseEntity.ok(commodityService.listActive());
    }
}
