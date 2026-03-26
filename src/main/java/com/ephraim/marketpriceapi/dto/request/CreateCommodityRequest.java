package com.ephraim.marketpriceapi.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCommodityRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String category;

    private String defaultUnit;
}
