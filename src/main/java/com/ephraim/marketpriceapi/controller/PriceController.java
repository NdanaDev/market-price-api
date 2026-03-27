package com.ephraim.marketpriceapi.controller;

import com.ephraim.marketpriceapi.dto.request.CreatePriceEntryRequest;
import com.ephraim.marketpriceapi.dto.response.ComparePriceResponse;
import com.ephraim.marketpriceapi.dto.response.LatestPriceResponse;
import com.ephraim.marketpriceapi.dto.response.PriceEntryResponse;
import com.ephraim.marketpriceapi.dto.response.PriceHistoryResponse;
import com.ephraim.marketpriceapi.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prices")
@RequiredArgsConstructor
@Tag(name = "Prices", description = "Submit and query commodity prices")
public class PriceController {

    private final PriceService priceService;

    @PostMapping
    @Operation(summary = "Submit a new price entry")
    public ResponseEntity<PriceEntryResponse> submit(@Valid @RequestBody CreatePriceEntryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(priceService.submit(request));
    }

    @GetMapping("/latest")
    @Operation(summary = "Get latest verified price for a commodity in a market")
    public ResponseEntity<LatestPriceResponse> getLatest(
            @RequestParam Long commodityId,
            @RequestParam Long marketId) {
        return ResponseEntity.ok(priceService.getLatest(commodityId, marketId));
    }

    @GetMapping("/history")
    @Operation(summary = "Get verified price history for a commodity in a market")
    public ResponseEntity<List<PriceHistoryResponse>> getHistory(
            @RequestParam Long commodityId,
            @RequestParam Long marketId,
            @RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(priceService.getHistory(commodityId, marketId, days));
    }

    @GetMapping("/compare")
    @Operation(summary = "Compare latest verified prices for a commodity across markets in a city")
    public ResponseEntity<List<ComparePriceResponse>> compare(
            @RequestParam Long commodityId,
            @RequestParam String city) {
        return ResponseEntity.ok(priceService.compareAcrossCity(commodityId, city));
    }
}
