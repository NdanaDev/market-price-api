package com.ephraim.marketpriceapi.service;

import com.ephraim.marketpriceapi.dto.request.CreateCommodityRequest;
import com.ephraim.marketpriceapi.dto.response.CommodityResponse;

import java.util.List;

public interface CommodityService {

    CommodityResponse create(CreateCommodityRequest request);

    List<CommodityResponse> listActive();
}
