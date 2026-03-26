package com.ephraim.marketpriceapi.service.impl;

import com.ephraim.marketpriceapi.config.CacheNames;
import com.ephraim.marketpriceapi.dto.request.CreateCommodityRequest;
import com.ephraim.marketpriceapi.dto.response.CommodityResponse;
import com.ephraim.marketpriceapi.entity.Commodity;
import com.ephraim.marketpriceapi.exception.DuplicateResourceException;
import com.ephraim.marketpriceapi.mapper.CommodityMapper;
import com.ephraim.marketpriceapi.repository.CommodityRepository;
import com.ephraim.marketpriceapi.service.CommodityService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommodityServiceImpl implements CommodityService {

    private final CommodityRepository commodityRepository;
    private final CommodityMapper commodityMapper;

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheNames.COMMODITIES_ACTIVE, allEntries = true)
    public CommodityResponse create(CreateCommodityRequest request) {
        if (commodityRepository.existsByName(request.getName().trim())) {
            throw new DuplicateResourceException(
                "Commodity already exists with name: " + request.getName());
        }
        Commodity saved = commodityRepository.save(commodityMapper.toEntity(request));
        return commodityMapper.toResponse(saved);
    }

    @Override
    @Cacheable(cacheNames = CacheNames.COMMODITIES_ACTIVE)
    public List<CommodityResponse> listActive() {
        return commodityRepository.findAllByIsActiveTrue()
                .stream()
                .map(commodityMapper::toResponse)
                .toList();
    }
}
