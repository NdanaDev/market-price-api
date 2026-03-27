package com.ephraim.marketpriceapi.mapper;

import com.ephraim.marketpriceapi.dto.request.CreateMarketRequest;
import com.ephraim.marketpriceapi.dto.response.MarketResponse;
import com.ephraim.marketpriceapi.entity.Market;
import org.springframework.stereotype.Component;

@Component
public class MarketMapper {

    public Market toEntity(CreateMarketRequest request) {
        return Market.builder()
                .name(request.getName().trim())
                .city(request.getCity().trim())
                .province(request.getProvince().trim())
                .country(request.getCountry() != null ? request.getCountry().trim() : "Zambia")
                .build();
    }

    public MarketResponse toResponse(Market market) {
        return MarketResponse.builder()
                .id(market.getId())
                .name(market.getName())
                .city(market.getCity())
                .province(market.getProvince())
                .country(market.getCountry())
                .isActive(market.isActive())
                .createdAt(market.getCreatedAt())
                .updatedAt(market.getUpdatedAt())
                .build();
    }
}
