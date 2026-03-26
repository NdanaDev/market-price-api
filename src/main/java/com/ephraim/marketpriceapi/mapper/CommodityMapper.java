package com.ephraim.marketpriceapi.mapper;

import com.ephraim.marketpriceapi.dto.request.CreateCommodityRequest;
import com.ephraim.marketpriceapi.dto.response.CommodityResponse;
import com.ephraim.marketpriceapi.entity.Commodity;
import org.springframework.stereotype.Component;

@Component
public class CommodityMapper {

    public Commodity toEntity(CreateCommodityRequest request) {
        return Commodity.builder()
                .name(request.getName().trim())
                .category(request.getCategory())
                .defaultUnit(request.getDefaultUnit())
                .build();
    }

    public CommodityResponse toResponse(Commodity commodity) {
        return CommodityResponse.builder()
                .id(commodity.getId())
                .name(commodity.getName())
                .category(commodity.getCategory())
                .defaultUnit(commodity.getDefaultUnit())
                .isActive(commodity.isActive())
                .createdAt(commodity.getCreatedAt())
                .updatedAt(commodity.getUpdatedAt())
                .build();
    }
}
