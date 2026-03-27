package com.ephraim.marketpriceapi.dto.response;

import com.ephraim.marketpriceapi.entity.VerificationStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PriceEntryResponse {

    private Long id;
    private Long commodityId;
    private String commodityName;
    private Long marketId;
    private String marketName;
    private String city;
    private Long agentId;
    private String agentName;
    private BigDecimal price;
    private String currency;
    private String unit;
    private LocalDateTime recordedAt;
    private String notes;
    private VerificationStatus verificationStatus;
    private LocalDateTime createdAt;
}
