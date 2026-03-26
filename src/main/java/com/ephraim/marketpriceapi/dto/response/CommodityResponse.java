package com.ephraim.marketpriceapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class CommodityResponse {

    private Long id;
    private String name;
    private String category;
    private String defaultUnit;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
