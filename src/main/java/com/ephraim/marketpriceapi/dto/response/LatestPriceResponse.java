package com.ephraim.marketpriceapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
