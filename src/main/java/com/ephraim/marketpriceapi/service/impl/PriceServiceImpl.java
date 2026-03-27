package com.ephraim.marketpriceapi.service.impl;

import com.ephraim.marketpriceapi.config.CacheNames;
import com.ephraim.marketpriceapi.dto.request.CreatePriceEntryRequest;
import com.ephraim.marketpriceapi.dto.response.ComparePriceResponse;
import com.ephraim.marketpriceapi.dto.response.LatestPriceResponse;
import com.ephraim.marketpriceapi.dto.response.PriceEntryResponse;
import com.ephraim.marketpriceapi.dto.response.PriceHistoryResponse;
import com.ephraim.marketpriceapi.entity.Commodity;
import com.ephraim.marketpriceapi.entity.Market;
import com.ephraim.marketpriceapi.entity.PriceEntry;
import com.ephraim.marketpriceapi.entity.VerificationStatus;
import com.ephraim.marketpriceapi.exception.ResourceNotFoundException;
import com.ephraim.marketpriceapi.mapper.PriceEntryMapper;
import com.ephraim.marketpriceapi.repository.CommodityRepository;
import com.ephraim.marketpriceapi.repository.MarketRepository;
import com.ephraim.marketpriceapi.repository.PriceEntryRepository;
import com.ephraim.marketpriceapi.service.AgentService;
import com.ephraim.marketpriceapi.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PriceServiceImpl implements PriceService {

    private final PriceEntryRepository priceEntryRepository;
    private final CommodityRepository commodityRepository;
    private final MarketRepository marketRepository;
    private final AgentService agentService;
    private final PriceEntryMapper priceEntryMapper;
    private final CacheManager cacheManager;

    @Override
    @Transactional
    public PriceEntryResponse submit(CreatePriceEntryRequest request) {
        Commodity commodity = commodityRepository.findByIdAndIsActiveTrue(request.getCommodityId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Commodity not found or inactive with id: " + request.getCommodityId()));

        Market market = marketRepository.findByIdAndIsActiveTrue(request.getMarketId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Market not found or inactive with id: " + request.getMarketId()));

        agentService.getActiveAgent(request.getAgentId());

        PriceEntry entry = PriceEntry.builder()
                .commodity(commodity)
                .market(market)
                .agent(agentService.getActiveAgent(request.getAgentId()))
                .price(request.getPrice())
                .currency(request.getCurrency())
                .unit(request.getUnit())
                .recordedAt(request.getRecordedAt())
                .notes(request.getNotes())
                .build();

        PriceEntry saved = priceEntryRepository.save(entry);

        evictPriceCaches(request.getCommodityId(), request.getMarketId(), market.getCity());

        return priceEntryMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.LATEST_PRICE,
               key = "'commodity_' + #commodityId + ':market_' + #marketId")
    public LatestPriceResponse getLatest(Long commodityId, Long marketId) {
        return priceEntryRepository
                .findTopByCommodityIdAndMarketIdAndVerificationStatusOrderByRecordedAtDesc(
                        commodityId, marketId, VerificationStatus.VERIFIED)
                .map(priceEntryMapper::toLatestPriceResponse)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No verified price found for commodity " + commodityId +
                        " in market " + marketId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PriceHistoryResponse> getHistory(Long commodityId, Long marketId, int days) {
        LocalDateTime from = LocalDateTime.now().minusDays(days);
        LocalDateTime to = LocalDateTime.now();
        return priceEntryRepository
                .findAllByCommodityIdAndMarketIdAndVerificationStatusAndRecordedAtBetweenOrderByRecordedAtAsc(
                        commodityId, marketId, VerificationStatus.VERIFIED, from, to)
                .stream()
                .map(priceEntryMapper::toHistoryResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = CacheNames.COMPARE_PRICES,
               key = "'commodity_' + #commodityId + ':city_' + #city")
    public List<ComparePriceResponse> compareAcrossCity(Long commodityId, String city) {
        return priceEntryRepository
                .findLatestVerifiedPricesPerMarketInCity(commodityId, city, VerificationStatus.VERIFIED)
                .stream()
                .map(priceEntryMapper::toComparePriceResponse)
                .toList();
    }

    private void evictPriceCaches(Long commodityId, Long marketId, String city) {
        Cache latestCache = cacheManager.getCache(CacheNames.LATEST_PRICE);
        if (latestCache != null) {
            latestCache.evict("commodity_" + commodityId + ":market_" + marketId);
        }
        Cache compareCache = cacheManager.getCache(CacheNames.COMPARE_PRICES);
        if (compareCache != null) {
            compareCache.evict("commodity_" + commodityId + ":city_" + city);
        }
    }
}
