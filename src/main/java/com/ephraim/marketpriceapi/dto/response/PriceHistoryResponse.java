package com.ephraim.marketpriceapi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PriceHistoryResponse {

    private Long id;
    private BigDecimal price;
    private String currency;
    private String unit;
    private LocalDateTime recordedAt;
}
