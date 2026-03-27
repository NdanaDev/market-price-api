package com.ephraim.marketpriceapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class LatestPriceResponse {

    private Long commodityId;
    private String commodityName;
    private Long marketId;
    private String marketName;
    private String city;
    private BigDecimal price;
    private String currency;
    private String unit;
    private LocalDateTime recordedAt;
}
