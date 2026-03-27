package com.ephraim.marketpriceapi.service.impl;

import com.ephraim.marketpriceapi.config.CacheNames;
import com.ephraim.marketpriceapi.dto.request.CreateMarketRequest;
import com.ephraim.marketpriceapi.dto.response.MarketResponse;
import com.ephraim.marketpriceapi.entity.Market;
import com.ephraim.marketpriceapi.exception.DuplicateResourceException;
import com.ephraim.marketpriceapi.mapper.MarketMapper;
import com.ephraim.marketpriceapi.repository.MarketRepository;
import com.ephraim.marketpriceapi.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {

    private final MarketRepository marketRepository;
    private final MarketMapper marketMapper;

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheNames.MARKETS_ACTIVE, allEntries = true)
    public MarketResponse create(CreateMarketRequest request) {
        if (marketRepository.existsByNameAndCity(request.getName().trim(), request.getCity().trim())) {
            throw new DuplicateResourceException(
                "Market already exists with name '" + request.getName() + "' in " + request.getCity());
        }
        Market saved = marketRepository.save(marketMapper.toEntity(request));
        return marketMapper.toResponse(saved);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.MARKETS_ACTIVE)
    public List<MarketResponse> listActive() {
        return marketRepository.findAllByIsActiveTrue()
                .stream()
                .map(marketMapper::toResponse)
                .toList();
    }
}
