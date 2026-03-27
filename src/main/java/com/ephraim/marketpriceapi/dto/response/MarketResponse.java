package com.ephraim.marketpriceapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MarketResponse {

    private Long id;
    private String name;
    private String city;
    private String province;
    private String country;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
