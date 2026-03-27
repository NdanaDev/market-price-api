package com.ephraim.marketpriceapi.service;

import com.ephraim.marketpriceapi.dto.request.CreateMarketRequest;
import com.ephraim.marketpriceapi.dto.response.MarketResponse;

import java.util.List;

public interface MarketService {

    MarketResponse create(CreateMarketRequest request);

    List<MarketResponse> listActive();
}
