package com.ephraim.marketpriceapi.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreatePriceEntryRequest {

    @NotNull(message = "Commodity ID is required")
    private Long commodityId;

    @NotNull(message = "Market ID is required")
    private Long marketId;

    @NotNull(message = "Agent ID is required")
    private Long agentId;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotBlank(message = "Currency is required")
    private String currency = "ZMW";

    @NotBlank(message = "Unit is required")
    private String unit;

    @NotNull(message = "Recorded date/time is required")
    private LocalDateTime recordedAt;

    private String notes;
}
