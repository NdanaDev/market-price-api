package com.ephraim.marketpriceapi.mapper;

import com.ephraim.marketpriceapi.dto.response.ComparePriceResponse;
import com.ephraim.marketpriceapi.dto.response.LatestPriceResponse;
import com.ephraim.marketpriceapi.dto.response.PriceEntryResponse;
import com.ephraim.marketpriceapi.dto.response.PriceHistoryResponse;
import com.ephraim.marketpriceapi.entity.PriceEntry;
import org.springframework.stereotype.Component;

@Component
public class PriceEntryMapper {

    public PriceEntryResponse toResponse(PriceEntry entry) {
        return PriceEntryResponse.builder()
                .id(entry.getId())
                .commodityId(entry.getCommodity().getId())
                .commodityName(entry.getCommodity().getName())
                .marketId(entry.getMarket().getId())
                .marketName(entry.getMarket().getName())
                .city(entry.getMarket().getCity())
                .agentId(entry.getAgent().getId())
                .agentName(entry.getAgent().getFullName())
                .price(entry.getPrice())
                .currency(entry.getCurrency())
                .unit(entry.getUnit())
                .recordedAt(entry.getRecordedAt())
                .notes(entry.getNotes())
                .verificationStatus(entry.getVerificationStatus())
                .createdAt(entry.getCreatedAt())
                .build();
    }

    public LatestPriceResponse toLatestPriceResponse(PriceEntry entry) {
        return LatestPriceResponse.builder()
                .commodityId(entry.getCommodity().getId())
                .commodityName(entry.getCommodity().getName())
                .marketId(entry.getMarket().getId())
                .marketName(entry.getMarket().getName())
                .city(entry.getMarket().getCity())
                .price(entry.getPrice())
                .currency(entry.getCurrency())
                .unit(entry.getUnit())
                .recordedAt(entry.getRecordedAt())
                .build();
    }

    public PriceHistoryResponse toHistoryResponse(PriceEntry entry) {
        return PriceHistoryResponse.builder()
                .id(entry.getId())
                .price(entry.getPrice())
                .currency(entry.getCurrency())
                .unit(entry.getUnit())
                .recordedAt(entry.getRecordedAt())
                .build();
    }

    public ComparePriceResponse toComparePriceResponse(PriceEntry entry) {
        return ComparePriceResponse.builder()
                .marketId(entry.getMarket().getId())
                .marketName(entry.getMarket().getName())
                .city(entry.getMarket().getCity())
                .price(entry.getPrice())
                .currency(entry.getCurrency())
                .unit(entry.getUnit())
                .recordedAt(entry.getRecordedAt())
                .build();
    }
}
