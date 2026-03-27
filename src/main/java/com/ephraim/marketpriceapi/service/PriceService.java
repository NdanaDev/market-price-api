package com.ephraim.marketpriceapi.service;

import com.ephraim.marketpriceapi.dto.request.CreatePriceEntryRequest;
import com.ephraim.marketpriceapi.dto.response.ComparePriceResponse;
import com.ephraim.marketpriceapi.dto.response.LatestPriceResponse;
import com.ephraim.marketpriceapi.dto.response.PriceEntryResponse;
import com.ephraim.marketpriceapi.dto.response.PriceHistoryResponse;

import java.util.List;

public interface PriceService {

    PriceEntryResponse submit(CreatePriceEntryRequest request);

    LatestPriceResponse getLatest(Long commodityId, Long marketId);

    List<PriceHistoryResponse> getHistory(Long commodityId, Long marketId, int days);

    List<ComparePriceResponse> compareAcrossCity(Long commodityId, String city);
}
